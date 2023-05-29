package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReviewMedia extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fileName;

    @ManyToOne
    @JoinColumn(name = "review_id")
    Review review;

    public ReviewMedia(String fileName, Review review) {
        this.fileName = fileName;
        this.review = review;
    }
}
