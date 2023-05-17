package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
