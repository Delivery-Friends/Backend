package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long score;
    String content;

    @ManyToOne
    @JoinColumn(name = "order_id")
    UserOrder order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
