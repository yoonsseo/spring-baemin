package hyunwoo.baemin.domain.User;

import hyunwoo.baemin.domain.Base.BaseEntity;
import hyunwoo.baemin.domain.Order.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
public class User extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<UserAddress> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<WishShop> wishShops = new ArrayList<>();

    private String name;

    private int nowAddress;

    private String phoneNum;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String password;

    public User() {

    }
}
