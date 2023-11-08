package com.umc.baemin.orders.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MenuDetailDto {
    private Long menuId;
    private String menuName;
    private int count;
    private List<String> options;
    private int price;
}
