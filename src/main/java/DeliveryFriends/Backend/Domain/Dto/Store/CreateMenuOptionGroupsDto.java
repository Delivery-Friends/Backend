package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateMenuOptionGroupsDto {
    String name;
    Long multiSelect;

    List<CreateMenuOptionsDto> menuOption;
}
