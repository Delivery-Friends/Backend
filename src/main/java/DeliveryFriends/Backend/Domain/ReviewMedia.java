package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ReviewMedia extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fileName;

    @ManyToOne
    @JoinColumn(name = "review_id")
    Review review;
}
