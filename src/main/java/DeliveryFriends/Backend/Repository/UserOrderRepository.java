package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Team;
import DeliveryFriends.Backend.Domain.User;
import DeliveryFriends.Backend.Domain.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    Optional<UserOrder> findByUserAndTeam(User user, Team team);

    List<UserOrder> findByUser(User user);
}
