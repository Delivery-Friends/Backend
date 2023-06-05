package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.User;
import DeliveryFriends.Backend.Domain.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
    Optional<UserReview> findByUserAndWriter(User user, User writer);
    List<UserReview> findByUser(User user);
}
