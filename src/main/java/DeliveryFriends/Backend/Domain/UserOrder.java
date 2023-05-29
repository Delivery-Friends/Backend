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

    @JoinColumn(name = "pay_id")
    @ManyToOne
    Pay pay;

    @JoinColumn(name = "store_id")
    @ManyToOne
    Store store;

    String order_info;
}
