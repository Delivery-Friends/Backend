package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Cart;
import DeliveryFriends.Backend.Domain.Store;
import DeliveryFriends.Backend.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndStoreAndDeleted(User user, Store store, Boolean Deleted);
    List<Cart> findByUserAndDeleted(User user, Boolean Deleted);
}
