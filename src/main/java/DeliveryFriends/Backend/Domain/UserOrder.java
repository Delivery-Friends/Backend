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

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    String paymentKey;

    @JoinColumn(name = "store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Store store;

    String orderInfo;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Team team;

    String menuInfo;
    Long price;
    Long deliveryTip;

    public UserOrder(User user, String paymentKey, Store store, String orderInfo, Team team, String menuInfo, Long price, Long deliveryTip) {
        this.user = user;
        this.paymentKey = paymentKey;
        this.store = store;
        this.orderInfo = orderInfo;
        this.team = team;
        this.menuInfo = menuInfo;
        this.price = price;
        this.deliveryTip = deliveryTip;
    }
}
