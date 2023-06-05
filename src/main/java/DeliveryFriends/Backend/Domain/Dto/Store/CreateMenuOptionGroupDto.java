package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateMenuOptionGroupDto {
    Long menuId;
    String name;
    Long multiSelect;
}
