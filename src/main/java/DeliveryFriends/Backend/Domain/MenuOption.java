package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MenuOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    Long price;
    Long maxCount;
    Long defaultValue;

    @JoinColumn(name = "menu_option_group_id")
    @ManyToOne
    MenuOptionGroup menuOptionGroup;

}
