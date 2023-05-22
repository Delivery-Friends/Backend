
package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class DislikeReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "post_reply_id")
    PostReply postReply;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
