package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuInfoAndPriceDto {
    String menuInfo;
    Long price;
    Long deliveryTip;

    public MenuInfoAndPriceDto(String menuInfo, Long price, Long deliveryTip) {
        this.menuInfo = menuInfo;
        this.price = price;
        this.deliveryTip = deliveryTip;
    }
}
