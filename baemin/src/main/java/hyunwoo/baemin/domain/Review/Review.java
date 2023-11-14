package hyunwoo.baemin.domain.Review;

import hyunwoo.baemin.domain.Base.BaseEntity;
import hyunwoo.baemin.domain.Shop.Shop;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Review extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "REVIEW_ID")
    private Long id;

    @OneToMany(mappedBy = "review")
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;
}
