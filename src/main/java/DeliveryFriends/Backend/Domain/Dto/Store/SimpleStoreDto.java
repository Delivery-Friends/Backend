package DeliveryFriends.Backend.Domain.Dto.Store;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleStoreDto {
    Long id;

    String name;
    Long deliveryWaitTime;
    Boolean packageAvailable;
    Long packageWaitTime;
    Long deliveryTip;
    Long reviewScore;
    Long minPrice;
    Long reviewCount;

    @Builder
    @QueryProjection

    public SimpleStoreDto(Long id, String name, Long deliveryWaitTime, Boolean packageAvailable, Long packageWaitTime, Long deliveryTip, Long reviewScore, Long minPrice, Long reviewCount) {
        this.id = id;
        this.name = name;
        this.deliveryWaitTime = deliveryWaitTime;
        this.packageAvailable = packageAvailable;
        this.packageWaitTime = packageWaitTime;
        this.deliveryTip = deliveryTip;
        this.reviewScore = reviewScore;
        this.minPrice = minPrice;
        this.reviewCount = reviewCount;
    }
}
