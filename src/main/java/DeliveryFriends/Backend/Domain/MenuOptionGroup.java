package DeliveryFriends.Backend.Domain;

import DeliveryFriends.Backend.Domain.Dto.Store.CreateMenuOptionGroupDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MenuOptionGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    Long multiSelect;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    Menu menu;

    public MenuOptionGroup(CreateMenuOptionGroupDto createMenuOptionGroupDto, Menu menu) {
        this.name = createMenuOptionGroupDto.getName();
        this.multiSelect = createMenuOptionGroupDto.getMultiSelect();
        this.menu = menu;
    }
}
