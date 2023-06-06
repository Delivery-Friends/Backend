package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByGroupEndTimeAfterAndLatitudeGreaterThanEqualAndLatitudeLessThanEqualAndLongitudeGreaterThanEqualAndLongitudeLessThanEqualAndOrderStatus(LocalDateTime nowTime, String lessLatitude, String greaterLatitude, String lessLongitude, String greaterLongitude, String orderStatus);

    List<Team> findByGroupEndTimeAfterAndOrderStatus(Pageable pageable, LocalDateTime groupEndTime, String orderStatus);
}
