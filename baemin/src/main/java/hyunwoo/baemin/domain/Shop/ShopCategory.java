package hyunwoo.baemin.domain.Shop;

import hyunwoo.baemin.domain.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ShopCategory extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "SHOP_CATEGORY_ID")
    private Long id;

    @OneToMany(mappedBy = "shopCategory")
    private List<ShopShopCategory> shops = new ArrayList<>();

}
