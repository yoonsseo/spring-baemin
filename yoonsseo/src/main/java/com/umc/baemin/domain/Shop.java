package com.umc.baemin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @NotNull
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

    @Builder
    public Shop(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
