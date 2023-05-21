package DeliveryFriends.Backend.Domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String result;

    @JoinColumn(name = "user_id")
    @ManyToOne
    User user;

    @JoinColumn(name = "choice_menu_id")
    @ManyToOne
    ChoiceMenu choiceMenu;

    @JoinColumn(name = "pay_id")
    @ManyToOne
    Pay pay;
}
