package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Review;
import DeliveryFriends.Backend.Domain.ReviewMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewMediaRepository extends JpaRepository<ReviewMedia, Long> {
    List<ReviewMedia> findByReview(Review review);
}
