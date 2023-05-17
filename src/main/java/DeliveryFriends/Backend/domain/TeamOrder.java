package DeliveryFriends.Backend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TeamOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Boolean isLeader;
    String orderStatus;

    @JoinColumn(name = "group_id")
    @ManyToOne
    Team group;

    @JoinColumn(name = "cart_id")
    @ManyToOne
    Cart cart;
}
