package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.ChoiceMenu;
import DeliveryFriends.Backend.Domain.ChoiceOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceOptionRepository extends JpaRepository<ChoiceOption, Long> {
    List<ChoiceOption> findByChoiceMenu(ChoiceMenu choiceMenu);
}
