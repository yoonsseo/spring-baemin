package com.umc.baemin.orders.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.umc.baemin.domain.Order;
import com.umc.baemin.domain.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OrderDto {
    @JsonFormat(pattern = "MM-dd")
    private LocalDateTime orderTime;
    private String orderStatus;
    private Long orderId;

    private String shopName;
    private Long shopId;
    private String menuName;
    private int menuNum;
    private int totalPrice;

    public OrderDto(Order order, Shop shop, String menuName, int menuNum) {
        this.orderTime = order.getCreatedAt();
        this.orderStatus = order.getOrderStatus().toString();
        this.orderId = order.getId();
        this.shopName = shop.getName();
        this.shopId = shop.getId();
        this.menuName = menuName;
        this.menuNum = menuNum;
        this.totalPrice = order.getTotalPrice();
    }
}
