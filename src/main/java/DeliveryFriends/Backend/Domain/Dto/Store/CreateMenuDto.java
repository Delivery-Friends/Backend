package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class CreateMenuDto {
    Long storeId;
    String name;
    Long price;
    String expression;
}
