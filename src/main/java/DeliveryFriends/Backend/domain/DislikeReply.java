
package DeliveryFriends.Backend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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
