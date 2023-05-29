package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Cart;
import DeliveryFriends.Backend.Domain.ChoiceMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceMenuRepository extends JpaRepository<ChoiceMenu, Long> {
    List<ChoiceMenu> findByCart(Cart cart);
}
