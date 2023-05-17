package DeliveryFriends.Backend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ChoiceMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long count;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    Menu menu;

    @ManyToOne
    @JoinColumn(name = "choice_option_id")
    ChoiceOption choiceOption;
}
