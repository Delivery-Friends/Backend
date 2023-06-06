package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMenuOptionsDto {
    String name;
    Long price;
    Long maxCount;
    Long defaultValue;
}
