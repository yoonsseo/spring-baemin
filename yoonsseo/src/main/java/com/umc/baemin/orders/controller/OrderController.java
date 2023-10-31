package com.umc.baemin.orders.controller;

import com.umc.baemin.orders.dto.TryRequestDto;
import com.umc.baemin.orders.dto.OrderRequestDto;
import com.umc.baemin.orders.dto.TryResponseDto;
import com.umc.baemin.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/try")
    public TryResponseDto tryToOrder(@RequestBody TryRequestDto tryRequestDto,
                                     @RequestParam("userId") Long userId) {
        return orderService.tryToOrder(tryRequestDto, userId);
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequestDto orderRequestDto,
                                            @RequestParam("userId") Long userId) {
        return orderService.createOrder(orderRequestDto, userId);
    }

//    @GetMapping("/status")
//    public
}
