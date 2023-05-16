package DeliveryFriends.Backend.Domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class likeStore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    Store store;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
