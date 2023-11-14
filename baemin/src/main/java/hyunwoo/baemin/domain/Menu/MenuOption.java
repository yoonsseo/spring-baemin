package hyunwoo.baemin.domain.Menu;

import hyunwoo.baemin.domain.Base.BaseOption;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class MenuOption extends BaseOption {
    @Id @GeneratedValue
    @Column(name = "MENU_OPTION_ID")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<MenuMenuOption> menuList = new ArrayList<>();
}
