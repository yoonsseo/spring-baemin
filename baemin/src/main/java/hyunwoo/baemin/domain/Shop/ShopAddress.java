package hyunwoo.baemin.domain.Shop;

import hyunwoo.baemin.domain.Base.BaseAddress;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ShopAddress extends BaseAddress {
    @Id @GeneratedValue
    @Column(name = "SHOP_ADDRESS_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Shop shop;
}
