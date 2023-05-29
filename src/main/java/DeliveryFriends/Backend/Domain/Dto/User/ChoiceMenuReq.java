package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChoiceMenuReq {
    Long menuId;
    Long count;
    List<ChoiceOptionReq> choiceOption;
}
