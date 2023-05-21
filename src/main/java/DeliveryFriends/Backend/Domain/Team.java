package DeliveryFriends.Backend.Domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime groupEndTime;

    @JoinColumn(name = "store_id")
    @ManyToOne
    Store store;
}
