package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Store;
import DeliveryFriends.Backend.Domain.StoreMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreMediaRepository extends JpaRepository<StoreMedia, Long>, StoreMediaRepositoryCustom {
}
