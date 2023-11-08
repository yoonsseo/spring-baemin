package com.umc.baemin.orders.service;

import com.umc.baemin.domain.*;
import com.umc.baemin.domain.enums.PayMethod;
import com.umc.baemin.domain.enums.ReceiveMethod;
import com.umc.baemin.orders.dto.detail.MenuDetailDto;
import com.umc.baemin.orders.dto.detail.OrderDetailResponseDto;
import com.umc.baemin.orders.dto.list.OrderDto;
import com.umc.baemin.orders.dto.list.OrderListResponseDto;
import com.umc.baemin.orders.dto.order.*;
import com.umc.baemin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ShopRepository shopRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OptionRepository optionRepository;
    private final OrderDetailOptionRepository orderDetailOptionRepository;

    @Transactional
    public TryResponseDto tryToOrder(TryRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).get(); //올바른 사용자가 넘어온다고 가정
        Address address = addressRepository.findById(user.getAddress_id()).get();

        return TryResponseDto.builder()
                .receiveMethod(requestDto.getReceiveMethod())
                .address(address.getAddress())
                .addressDetail(address.getAddressDetail())
                .phone(user.getPhone())
                .orderPrice(requestDto.getOrderPrice())
                .deliveryFee(requestDto.getDeliveryFee())
                .totalPrice(requestDto.getTotalPrice())
                .build();
    }

    @Transactional
    public ResponseEntity<Void> createOrder(OrderRequestDto orderRequestDto, Long userId) {
        User user = userRepository.findById(userId).get(); //올바른 사용자가 넘어온다고 가정
        Shop shop = shopRepository.findById(orderRequestDto.getShopId()).get();

        PayMethod payMethod = PayMethod.valueOf(orderRequestDto.getPayMethod());
        ReceiveMethod receiveMethod = ReceiveMethod.valueOf(orderRequestDto.getReceiveMethod());

        Order order = Order.builder()
                .request(orderRequestDto.getRequest())
                .payMethod(payMethod)
                .orderPrice(orderRequestDto.getOrderPrice())
                .deliveryFee(orderRequestDto.getDeliveryFee())
                .totalPrice(orderRequestDto.getTotalPrice())
                .deliveryAddress(orderRequestDto.getAddress() + " " + orderRequestDto.getAddressDetail())
                .eta(orderRequestDto.getEta())
                .receiveMethod(receiveMethod)
                .user(user)
                .shop(shop)
                .build();
        orderRepository.save(order);

        for (int i = 0; i < orderRequestDto.getMenus().size(); i++) {
            MenuDto menuDto = orderRequestDto.getMenus().get(i);
            Menu menu = menuRepository.findById(menuDto.getMenuId()).get();
            List<Long> optionIds = menuDto.getOptionIds();
            List<Integer> optionPrices = optionIds.stream().map(optionId -> optionRepository.findById(optionId).get().getPrice()).toList();
            int orderDetailPrice = 0;
            for (Integer optionPrice : optionPrices) {
                orderDetailPrice += optionPrice;
            }
            orderDetailPrice += menu.getPrice();

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .menu(menu)
                    .count(menuDto.getCount())
                    .price(orderDetailPrice)
                    .build();
            orderDetailRepository.save(orderDetail);

            for (int j = 0; j < menuDto.getOptionIds().size(); j++) {
                Long optionId = menuDto.getOptionIds().get(i);
                Option option = optionRepository.findById(optionId).get();

                OrderDetailOption orderDetailOption = OrderDetailOption.builder()
                        .orderDetail(orderDetail)
                        .option(option)
                        .build();
                orderDetailOptionRepository.save(orderDetailOption);
            }


        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public OrderStatusResponseDto getOrderStatus(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId).get();
        LocalDateTime time = order.getCreatedAt().plusMinutes(order.getEta());
        String menuName = orderDetailRepository.findByOrderId(orderId).get(0).getMenu().getName();
        int menuNum = orderDetailRepository.findByOrderId(orderId).size();

        return OrderStatusResponseDto.builder()
                .receiveMethod(order.getReceiveMethod().toString())
                .orderStatus(order.getOrderStatus().toString())
                .eta(order.getEta())
                .time(time)
                .orderId(orderId)
                .address(order.getDeliveryAddress())
                .request(order.getRequest())
                .shopName(order.getShop().getName())
                .menuName(menuName)
                .menuNum(menuNum)
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public OrderDetailResponseDto getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        String menuName = orderDetailRepository.findByOrderId(orderId).get(0).getMenu().getName();
        int menuNum = orderDetailRepository.findByOrderId(orderId).size();
        List<MenuDetailDto> menus = new ArrayList<>();
        for (int i = 0; i < menuNum; i++) {
            OrderDetail orderDetail = orderDetailRepository.findByOrderId(orderId).get(i);
            Menu menu = orderDetail.getMenu();
            List<OrderDetailOption> orderDetailOptions = orderDetailOptionRepository.findByOrderDetailId(orderDetail.getId());
            List<String> options = orderDetailOptions.stream().map(OrderDetailOption -> OrderDetailOption.getOption().getName()).toList();

            MenuDetailDto menuDetailDto = MenuDetailDto.builder()
                    .menuId(menu.getId())
                    .menuName(menu.getName())
                    .count(orderDetail.getCount())
                    .options(options)
                    .price(orderDetail.getPrice())
                    .build();
            menus.add(menuDetailDto);
        }

        return OrderDetailResponseDto.builder()
                .orderStatus(order.getOrderStatus().toString())
                .shopName(order.getShop().getName())
                .menuName(menuName)
                .menuNum(menuNum)
                .orderTime(order.getCreatedAt())
                .orderId(orderId)
                .receiveMethod(order.getReceiveMethod().toString())
                .menus(menus)
                .orderPrice(order.getOrderPrice())
                .deliveryFee(order.getDeliveryFee())
                .totalPrice(order.getTotalPrice())
                .payMethod(order.getPayMethod().toString())
                .deliveryAddress(order.getDeliveryAddress())
                .phone(order.getUser().getPhone())
                .request(order.getRequest())
                .build();
    }

    public OrderListResponseDto getOrderList(Long userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUser(userId, pageable);
        Page<OrderDto> orderDtos = orders.map(order -> new OrderDto(order,
                        shopRepository.findById(order.getShop().getId()).get(),
                        orderDetailRepository.findByOrderId(order.getId()).get(0).getMenu().getName(),
                        orderDetailRepository.findByOrderId(order.getId()).size()));
        return new OrderListResponseDto(orderDtos.getTotalPages(), orderDtos.getNumber(), orderDtos.getContent());
    }
}
