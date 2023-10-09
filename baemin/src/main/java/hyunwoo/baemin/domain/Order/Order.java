package hyunwoo.baemin.domain.Order;

import hyunwoo.baemin.domain.Base.BaseEntity;
import hyunwoo.baemin.domain.Shop.Shop;
import hyunwoo.baemin.domain.User.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Order extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  // 한 주문에서 복수의 가게에서 시킬 수 없어서 shop 이랑 연결
    private Shop shop;

}
