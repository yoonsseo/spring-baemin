package hyunwoo.baemin.domain.Review;

import hyunwoo.baemin.domain.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ReviewPhoto extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "REVIEW_PHOTO_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    private String image;
}
