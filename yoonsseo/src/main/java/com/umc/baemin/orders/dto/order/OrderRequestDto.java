package com.umc.baemin.orders.dto.order;

import com.umc.baemin.orders.dto.order.MenuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long shopId;
    private List<MenuDto> menus;
    private String receiveMethod;
    private int eta;

    private String address;
    private String addressDetail;
    private String phone;

    private String request;
    private String payMethod;

    private int orderPrice;
    private int deliveryFee;
    private int totalPrice;
}
