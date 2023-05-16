package DeliveryFriends.Backend.Domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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
