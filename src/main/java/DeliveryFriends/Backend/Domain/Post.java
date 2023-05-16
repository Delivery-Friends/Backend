package DeliveryFriends.Backend.Domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String content;

    @ManyToOne
    @JoinColumn(name = "store_id")
    Store store;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String region1depth;
    String region2depth;
    String region3depth;

    Long likeCount;
    Long dislikeCount;
}
