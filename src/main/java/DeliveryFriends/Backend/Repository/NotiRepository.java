package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Noti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiRepository extends JpaRepository<Noti, Long> {
}
