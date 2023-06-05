package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.LikeUser;
import DeliveryFriends.Backend.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeUserRepository extends JpaRepository<LikeUser, Long> {
    Optional<LikeUser> findByReceiverAndSender(User receiver, User sender);

    List<LikeUser> findBySender(User sender);

    Long countByReceiver(User user);
}
