package DeliveryFriends.Backend.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String result;

    @JoinColumn(name = "user_id")
    @ManyToOne
    User user;

    String paymentKey;

    @JoinColumn(name = "store_id")
    @ManyToOne
    Store store;

    String orderInfo;

    @JoinColumn(name = "team_id")
    @ManyToOne
    Team team;

    public UserOrder(String result, User user, String paymentKey, Store store, String orderInfo, Team team) {
        this.result = result;
        this.user = user;
        this.paymentKey = paymentKey;
        this.store = store;
        this.orderInfo = orderInfo;
        this.team = team;
    }
}
