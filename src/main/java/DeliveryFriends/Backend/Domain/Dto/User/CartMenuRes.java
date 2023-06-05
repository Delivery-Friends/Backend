package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CartMenuRes {
    String name;
    Long price;
    Long count;
    List<CartOptionRes> options;
    List<String> medium;
}
