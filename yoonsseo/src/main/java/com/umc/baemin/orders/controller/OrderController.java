package com.umc.baemin.orders.controller;

import com.umc.baemin.orders.dto.detail.OrderDetailResponseDto;
import com.umc.baemin.orders.dto.list.OrderListResponseDto;
import com.umc.baemin.orders.dto.order.OrderRequestDto;
import com.umc.baemin.orders.dto.order.OrderStatusResponseDto;
import com.umc.baemin.orders.dto.order.TryRequestDto;
import com.umc.baemin.orders.dto.order.TryResponseDto;
import com.umc.baemin.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 장바구니 -> 주문하기
    @PostMapping("/try")
    public TryResponseDto tryToOrder(@RequestBody TryRequestDto tryRequestDto,
                                     @RequestParam("userId") Long userId) {
        return orderService.tryToOrder(tryRequestDto, userId);
    }

    // 주문하기 -> 결제
    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequestDto orderRequestDto,
                                            @RequestParam("userId") Long userId) {
        return orderService.createOrder(orderRequestDto, userId);
    }

    // 주문 현황
    @GetMapping("/status")
    public OrderStatusResponseDto getOrderStatus(@RequestParam("orderId") Long orderId,
                                                 @RequestParam("userId") Long userId) {
        return orderService.getOrderStatus(orderId, userId);
    }

    // 주문 상세
    @GetMapping("/{orderId}")
    public OrderDetailResponseDto getOrderDetail(@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }


    // 모든 주문 내역
    @GetMapping("/orderList")
    public OrderListResponseDto getOrderList(@RequestParam("userId") Long userId,
                                             @RequestParam("pageNo") int pageNo) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(pageNo, 9, sort);
        return orderService.getOrderList(userId, pageable);
    }
}
