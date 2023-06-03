package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserOrderDto {
    String result;
    String paymentKey;
    String storeName;
    String orderInfo;

    public UserOrderDto(String result, String paymentKey, String storeName, String orderInfo) {
        this.result = result;
        this.paymentKey = paymentKey;
        this.storeName = storeName;
        this.orderInfo = orderInfo;
    }
}
