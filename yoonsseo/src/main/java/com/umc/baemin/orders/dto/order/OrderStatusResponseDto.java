package com.umc.baemin.orders.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusResponseDto {
    private String receiveMethod;
    private String orderStatus;
    private int eta;
    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime time;

    private Long orderId;

    private String address;
    private String request;

    private String shopName;
    private String menuName;
    private int menuNum;
    private int totalPrice;
}
