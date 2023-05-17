package DeliveryFriends.Backend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ChoiceOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long count;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "menu_option_id")
    MenuOption menuOption;

    @ManyToOne
    @JoinColumn(name = "menu_option_group_id")
    MenuOptionGroup menuOptionGroup;
}
