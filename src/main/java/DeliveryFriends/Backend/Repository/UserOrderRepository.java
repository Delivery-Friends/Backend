package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
}
