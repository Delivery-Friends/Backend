package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChoiceOptionReq {
    Long count;
    Long menuOptionId;
}
