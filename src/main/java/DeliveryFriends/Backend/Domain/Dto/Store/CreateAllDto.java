package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateAllDto {
    CreateStoreDto store;
    List<CreateMenusDto> menu;
}
