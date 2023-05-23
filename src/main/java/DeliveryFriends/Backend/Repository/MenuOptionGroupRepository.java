package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Menu;
import DeliveryFriends.Backend.Domain.MenuOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionGroupRepository extends JpaRepository<MenuOptionGroup, Long> {
    List<MenuOptionGroup> findByMenuAndDeleted(Menu menu, Boolean deleted);
}
