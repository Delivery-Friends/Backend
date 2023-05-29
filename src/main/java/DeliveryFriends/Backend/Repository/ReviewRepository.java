package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Review;
import DeliveryFriends.Backend.Domain.Store;
import DeliveryFriends.Backend.Domain.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStore(Store store);

    Optional<Review> findByOrder(UserOrder order);
}
