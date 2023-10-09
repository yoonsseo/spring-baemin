package hyunwoo.baemin.domain.User;

import hyunwoo.baemin.domain.Base.BaseAddress;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserAddress extends BaseAddress {
    @Id @GeneratedValue
    @Column(name = "USER_ADDRESS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
