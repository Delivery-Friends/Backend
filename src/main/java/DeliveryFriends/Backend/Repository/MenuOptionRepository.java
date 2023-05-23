package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.MenuOption;
import DeliveryFriends.Backend.Domain.MenuOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
    List<MenuOption> findByMenuOptionGroupAndDeleted(MenuOptionGroup menuOptionGroup, Boolean deleted);
}
