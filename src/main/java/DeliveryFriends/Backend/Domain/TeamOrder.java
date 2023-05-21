package DeliveryFriends.Backend.Domain;

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

    @JoinColumn(name = "team_id")
    @ManyToOne
    Team team;

    @JoinColumn(name = "cart_id")
    @ManyToOne
    Cart cart;
}
