package hyunwoo.baemin.domain.Order;

import hyunwoo.baemin.domain.Base.BaseEntity;
import hyunwoo.baemin.domain.Menu.Menu;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MenuOrderDetail extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "MENU_ORDER_DETAIL")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderDetail orderDetail;
}
