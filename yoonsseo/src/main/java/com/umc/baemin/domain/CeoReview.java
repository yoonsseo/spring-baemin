package com.umc.baemin.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CeoReview extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ceo_review_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}
