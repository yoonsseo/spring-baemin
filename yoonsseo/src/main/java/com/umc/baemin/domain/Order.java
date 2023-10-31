package com.umc.baemin.domain;

import com.umc.baemin.domain.enums.OrderStatus;
import com.umc.baemin.domain.enums.PayMethod;
import com.umc.baemin.domain.enums.ReceiveMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String request;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private PayMethod payMethod;

    @NotNull
    private int orderPrice;

    @NotNull
    private int deliveryFee;

    @NotNull
    private int totalPrice;

    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("\"RECEPTION\"")
    private OrderStatus orderStatus;

    @NotNull
    private String deliveryAddress;

    @NotNull
    private int eta;

    @Enumerated(value = EnumType.STRING)
    private ReceiveMethod receiveMethod;

    @Column(columnDefinition = "TINYINT(0) DEFAULT 0")
    private boolean isDel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
