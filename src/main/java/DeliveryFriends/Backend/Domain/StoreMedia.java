package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class StoreMedia extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    Store store;

    String fileName;
}
