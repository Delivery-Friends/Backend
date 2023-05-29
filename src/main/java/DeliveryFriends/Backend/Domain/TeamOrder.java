package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

    public TeamOrder(Boolean isLeader, String orderStatus, Team team, Cart cart) {
        this.isLeader = isLeader;
        this.orderStatus = orderStatus;
        this.team = team;
        this.cart = cart;
    }
}
