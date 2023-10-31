package com.umc.baemin.orders.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private Long menuId;
    private int count;

    private List<Long> optionIds;
}
