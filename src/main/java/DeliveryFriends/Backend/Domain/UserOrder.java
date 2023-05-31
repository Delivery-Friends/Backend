package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
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

    String order_info;

    public UserOrder(String result, User user, String paymentKey, Store store, String order_info) {
        this.result = result;
        this.user = user;
        this.paymentKey = paymentKey;
        this.store = store;
        this.order_info = order_info;
    }
}
