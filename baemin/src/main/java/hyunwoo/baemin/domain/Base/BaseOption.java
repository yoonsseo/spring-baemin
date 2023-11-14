package hyunwoo.baemin.domain.Base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class BaseOption extends BaseEntity{

    private String name;
    private Integer price;
}
