package com.umc.baemin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class Shop extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    private String name;
    private String openingHour;
    private String dayOff;
    private String phone;
    private String deliveryArea;
    private String description;
    private String notice;

    @ColumnDefault("0")
    private int reviewTotal;

    @ColumnDefault("0")
    private int reviewCount;

    @ColumnDefault("0")
    private int wishCount;

    @ColumnDefault("0")
    private int minimum;
}
