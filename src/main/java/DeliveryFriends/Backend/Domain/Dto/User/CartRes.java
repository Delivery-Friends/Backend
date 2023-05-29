package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CartRes {
    Long cartId;
    Long storeId;
    List<CartMenuRes> menus;
}
