package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Team;
import DeliveryFriends.Backend.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(String kakaoId);
    List<User> findByTeam(Team team);
}
