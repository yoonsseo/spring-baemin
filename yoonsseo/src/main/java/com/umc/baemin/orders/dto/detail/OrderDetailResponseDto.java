package com.umc.baemin.orders.dto.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.umc.baemin.orders.dto.detail.MenuDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponseDto {
    private String orderStatus;
    private String shopName;
    private String menuName;
    private int menuNum;
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime orderTime;
    private Long orderId;
    private String receiveMethod;

    private List<MenuDetailDto> menus;

    private int orderPrice;
    private int deliveryFee;
    private int totalPrice;
    private String payMethod;

    private String deliveryAddress;

    private String phone;

    private String request;
}
