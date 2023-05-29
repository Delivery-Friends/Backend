package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.PopularCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PopularCategoryRepository extends JpaRepository<PopularCategory, Long> {
    Long countByCategoryAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(String category, LocalDateTime startTime, LocalDateTime nowTime);
}
