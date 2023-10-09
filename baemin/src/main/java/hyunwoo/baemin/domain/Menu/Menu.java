package hyunwoo.baemin.domain.Menu;

import hyunwoo.baemin.domain.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Menu extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "MENU_ID")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuCategory menuCategory;

    @OneToMany(mappedBy = "menu")
    private List<MenuMenuOption> MenuOptionList = new ArrayList<>();

}
