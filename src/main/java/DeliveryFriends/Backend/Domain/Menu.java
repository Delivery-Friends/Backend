package DeliveryFriends.Backend.Domain;

import DeliveryFriends.Backend.Domain.Dto.Store.CreateMenuDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    Long price;
    String expression;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    Store store;

    public Menu(CreateMenuDto createMenuDto, Store store) {
        this.name = createMenuDto.getName();
        this.price = createMenuDto.getPrice();
        this.expression = createMenuDto.getExpression();
        this.store = store;
    }
}
