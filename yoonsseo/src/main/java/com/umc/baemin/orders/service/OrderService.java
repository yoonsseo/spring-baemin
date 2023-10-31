package com.umc.baemin.orders.service;

import com.umc.baemin.domain.*;
import com.umc.baemin.domain.enums.PayMethod;
import com.umc.baemin.domain.enums.ReceiveMethod;
import com.umc.baemin.orders.dto.MenuDto;
import com.umc.baemin.orders.dto.TryRequestDto;
import com.umc.baemin.orders.dto.OrderRequestDto;
import com.umc.baemin.orders.dto.TryResponseDto;
import com.umc.baemin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .menu(menu)
                    .count(menuDto.getCount())
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

}
