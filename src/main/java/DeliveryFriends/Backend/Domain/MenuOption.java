package DeliveryFriends.Backend.Domain;

import DeliveryFriends.Backend.Domain.Dto.Store.CreateMenuOptionDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MenuOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    Long price;
    Long maxCount;
    Long defaultValue;

    @JoinColumn(name = "menu_option_group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    MenuOptionGroup menuOptionGroup;

    public MenuOption(CreateMenuOptionDto createMenuOptionDto, MenuOptionGroup menuOptionGroup) {
        this.name = createMenuOptionDto.getName();
        this.price = createMenuOptionDto.getPrice();
        this.maxCount = createMenuOptionDto.getMaxCount();
        this.defaultValue = createMenuOptionDto.getDefaultValue();
        this.menuOptionGroup = menuOptionGroup;
    }

    public MenuOption(String name, Long price, Long maxCount, Long defaultValue, MenuOptionGroup menuOptionGroup) {
        this.name = name;
        this.price = price;
        this.maxCount = maxCount;
        this.defaultValue = defaultValue;
        this.menuOptionGroup = menuOptionGroup;
    }
}
