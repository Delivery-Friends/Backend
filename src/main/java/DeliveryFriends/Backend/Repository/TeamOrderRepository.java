package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.TeamOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamOrderRepository extends JpaRepository<TeamOrder, Long> {
}
