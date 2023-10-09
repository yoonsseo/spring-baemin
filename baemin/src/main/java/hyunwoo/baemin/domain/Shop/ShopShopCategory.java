package hyunwoo.baemin.domain.Shop;

import hyunwoo.baemin.domain.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ShopShopCategory extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "SHOP_SHOP_CATEGORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShopCategory shopCategory;
}
