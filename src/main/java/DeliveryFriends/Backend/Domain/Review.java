package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long score;
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    UserOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne
    Store store;

    public Review(Long score, String content, UserOrder order, User user, Store store) {
        this.score = score;
        this.content = content;
        this.order = order;
        this.user = user;
        this.store = store;
    }
}
