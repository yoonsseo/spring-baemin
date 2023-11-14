package hyunwoo.baemin.domain.Shop;

import hyunwoo.baemin.domain.Base.BaseEntity;
import hyunwoo.baemin.domain.Order.Order;
import hyunwoo.baemin.domain.Review.Review;
import hyunwoo.baemin.domain.User.WishShop;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Shop extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "SHOP_ID")
    private Long id;

    @OneToMany(mappedBy = "shop")  // 다대다 연결 위해 ShopShopCategory 로 연결
    private List<ShopShopCategory> shopCategories = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private ShopAddress shopAddress;

    @OneToMany(mappedBy = "shop")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    private List<WishShop> wishedList = new ArrayList<>();
}
