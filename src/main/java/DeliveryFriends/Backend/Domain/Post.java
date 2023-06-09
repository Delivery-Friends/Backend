package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    @Column(columnDefinition = "TEXT")
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    String region1depth;
    String region2depth;
    String region3depth;

    Long likeCount;
    Long dislikeCount;
}
