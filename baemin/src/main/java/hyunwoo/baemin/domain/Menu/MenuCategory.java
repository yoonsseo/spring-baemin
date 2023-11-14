package hyunwoo.baemin.domain.Menu;

import hyunwoo.baemin.domain.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class MenuCategory extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "MENU_CATEGORY_ID")
    private Long id;

    @OneToMany(mappedBy = "menuCategory")
    private List<Menu> menuList = new ArrayList<>();


}
