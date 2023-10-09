package hyunwoo.baemin.domain.Order;

import hyunwoo.baemin.domain.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class OrderDetail extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "ORDER_DETAIL_ID")
    private Long id;

    @OneToMany(mappedBy = "orderDetail")
    private List<MenuOrderDetail> menuList = new ArrayList<>();
}
