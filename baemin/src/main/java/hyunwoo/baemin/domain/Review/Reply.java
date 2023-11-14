package hyunwoo.baemin.domain.Review;

import hyunwoo.baemin.domain.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Reply extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "REPLY_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Review review;  // 사장님이 댓글 하나만 달기 때문에 OneToOne

}
