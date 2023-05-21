package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(String kakaoId);
}
