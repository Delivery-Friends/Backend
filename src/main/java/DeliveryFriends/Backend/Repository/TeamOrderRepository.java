package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Cart;
import DeliveryFriends.Backend.Domain.Team;
import DeliveryFriends.Backend.Domain.TeamOrder;
import DeliveryFriends.Backend.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamOrderRepository extends JpaRepository<TeamOrder, Long> {
    Optional<TeamOrder> findByTeamAndUser(Team team, User user);

    List<TeamOrder> findByCart(Cart cart);
}
