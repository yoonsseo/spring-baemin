package hyunwoo.baemin.domain.Menu;

import hyunwoo.baemin.domain.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MenuMenuOption extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "MENU_MENU_OPTION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuOption menuOption;
}
