package hyunwoo.baemin.domain.User;

import hyunwoo.baemin.domain.Base.BaseEntity;
import hyunwoo.baemin.domain.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<UserAddress> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
