package DeliveryFriends.Backend.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UserReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    User writer;

    @Column(columnDefinition = "TEXT")
    String context;

    Long score;

    public UserReview(User user, User writer, String context, Long score) {
        this.user = user;
        this.writer = writer;
        this.context = context;
        this.score = score;
    }
}
