package hyunwoo.baemin.domain.Base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class BaseAddress extends BaseEntity {
    private String state;  // 도
    private String city;  // 시
    private String town;  // 구
    private String street;  // 동
    private String detail;  // 상세주소
}
