package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String price;
    String expression;

    @ManyToOne
    @JoinColumn(name = "store_id")
    Store store;
}
