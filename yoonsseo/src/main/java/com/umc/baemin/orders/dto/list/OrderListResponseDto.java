package com.umc.baemin.orders.dto.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OrderListResponseDto {
    private int totalPage;
    private int currentPage;
    private List<OrderDto> orders;
}
