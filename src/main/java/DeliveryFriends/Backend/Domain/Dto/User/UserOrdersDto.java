package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserOrdersDto {
    String result;
    String storeName;
    String orderInfo;

    public UserOrdersDto(String result, String storeName, String orderInfo) {
        this.result = result;
        this.storeName = storeName;
        this.orderInfo = orderInfo;
    }
}
