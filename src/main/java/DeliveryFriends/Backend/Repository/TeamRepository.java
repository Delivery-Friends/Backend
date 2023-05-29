package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
