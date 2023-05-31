package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Menu;
import DeliveryFriends.Backend.Domain.MenuMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuMediaRepository extends JpaRepository<MenuMedia, Long> {
    List<MenuMedia> findByMenu(Menu menu);
}
