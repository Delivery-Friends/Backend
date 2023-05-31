package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Team;
import DeliveryFriends.Backend.Domain.TeamOrder;
import DeliveryFriends.Backend.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamOrderRepository extends JpaRepository<TeamOrder, Long> {
    Optional<TeamOrder> findByTeamAndUser(Team team, User user);
}
