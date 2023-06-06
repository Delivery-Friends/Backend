package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuInfoAndPriceDto {
    Long orderId;
    String username;
    String menuInfo;
    Long price;
    Long deliveryTip;

    public MenuInfoAndPriceDto(Long orderId, String username, String menuInfo, Long price, Long deliveryTip) {
        this.orderId = orderId;
        this.username = username;
        this.menuInfo = menuInfo;
        this.price = price;
        this.deliveryTip = deliveryTip;
    }
}
