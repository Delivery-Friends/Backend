package DeliveryFriends.Backend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PostReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
