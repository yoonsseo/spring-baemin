package com.umc.baemin.domain;

import com.umc.baemin.domain.enums.UserGrade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String phone;

    private String email;

    @NotNull
    private String nickname;

    private String image;

    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("\"THANKS\"")
    private UserGrade userGrade;

    @ColumnDefault("0")
    private int reviewCount;

    @ColumnDefault("0")
    private int reviewTotal;

    //현재 주소 나타내기 위함
    private Long address_id;

    @Builder
    public User(String phone, String nickname) {
        this.phone = phone;
        this.nickname = nickname;
    }
}
