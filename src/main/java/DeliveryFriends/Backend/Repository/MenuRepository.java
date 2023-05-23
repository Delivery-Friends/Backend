package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Menu;
import DeliveryFriends.Backend.Domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    public List<Menu> findByStoreAndDeleted(Store store, Boolean deleted);
}
