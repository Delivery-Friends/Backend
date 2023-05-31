package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.LikeStore;
import DeliveryFriends.Backend.Domain.Store;
import DeliveryFriends.Backend.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeStoreRepository extends JpaRepository<LikeStore, Long> {
    Optional<LikeStore> findByStoreAndUser(Store store, User user);

    List<LikeStore> findByUser(User user);
}
