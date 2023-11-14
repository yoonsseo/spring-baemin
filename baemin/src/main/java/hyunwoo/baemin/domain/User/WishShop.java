package hyunwoo.baemin.domain.User;

import hyunwoo.baemin.domain.Base.BaseEntity;
import hyunwoo.baemin.domain.Shop.Shop;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class WishShop extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "WISH_SHOP_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;
}
