package com.umc.baemin.orders.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TryRequestDto {
    private String receiveMethod;

    private int orderPrice;
    private int deliveryFee;
    private int totalPrice;
}
