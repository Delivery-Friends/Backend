package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ChoiceOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice_menu_id")
    ChoiceMenu choiceMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_option_id")
    MenuOption menuOption;

    public ChoiceOption(Long count, User user, ChoiceMenu choiceMenu, MenuOption menuOption) {
        this.count = count;
        this.user = user;
        this.choiceMenu = choiceMenu;
        this.menuOption = menuOption;
    }
}
