package com.umc.baemin.orders.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TryResponseDto {
    private String receiveMethod;

    private String address;
    private String addressDetail;
    private String phone;

    private int orderPrice;
    private int deliveryFee;
    private int totalPrice;
}
