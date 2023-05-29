package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartReq {
    Long storeId;
    List<ChoiceMenuReq> menu;
}
