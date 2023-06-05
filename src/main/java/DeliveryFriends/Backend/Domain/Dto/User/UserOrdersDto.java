package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UserOrdersDto {
    Long storeId;
    String storeName;
    List<String> medium;
    Long leaderId;
    Long orderId;
    String orderInfo;
    LocalDateTime createdDate;
    String menuInfo;
    Long price;
    Long deliveryTip;
    Boolean isStoreReviewWrite;
    Boolean isUserReviewWrite;

    public UserOrdersDto(Long storeId, String storeName, List<String> medium, Long leaderId, Long orderId, String orderInfo, LocalDateTime createdDate, String menuInfo, Long price, Long deliveryTip, Boolean isStoreReviewWrite, Boolean isUserReviewWrite) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.medium = medium;
        this.leaderId = leaderId;
        this.orderId = orderId;
        this.orderInfo = orderInfo;
        this.createdDate = createdDate;
        this.menuInfo = menuInfo;
        this.price = price;
        this.deliveryTip = deliveryTip;
        this.isStoreReviewWrite = isStoreReviewWrite;
        this.isUserReviewWrite = isUserReviewWrite;
    }
}
