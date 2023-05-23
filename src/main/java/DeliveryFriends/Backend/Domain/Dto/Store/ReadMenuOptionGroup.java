package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReadMenuOptionGroup {
    Long id;

    String name;
    Long multiSelect;

    List<ReadMenuOption> readMenuOptionList;
}
