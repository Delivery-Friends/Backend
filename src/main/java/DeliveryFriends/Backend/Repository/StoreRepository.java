package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {

}