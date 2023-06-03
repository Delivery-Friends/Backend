package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Cart;
import DeliveryFriends.Backend.Domain.ChoiceMenu;
import DeliveryFriends.Backend.Domain.Menu;
import DeliveryFriends.Backend.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChoiceMenuRepository extends JpaRepository<ChoiceMenu, Long> {
    List<ChoiceMenu> findByCart(Cart cart);

    Optional<ChoiceMenu> findByUserAndMenuAndCart(User user, Menu menu, Cart cart);
}
