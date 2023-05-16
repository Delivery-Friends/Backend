package DeliveryFriends.Backend.Domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class GroupOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Boolean isLeader;
    String orderStatus;

    @JoinColumn(name = "group_id")
    @ManyToOne
    Group group;

    @JoinColumn(name = "cart_id")
    @ManyToOne
    Cart cart;
}
