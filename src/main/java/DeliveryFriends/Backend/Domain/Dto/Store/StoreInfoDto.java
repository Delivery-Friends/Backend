package DeliveryFriends.Backend.Domain.Dto.Store;

import DeliveryFriends.Backend.Domain.Store;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class StoreInfoDto {
    Long id;

    String name;
    String region1depthName;
    String region2depthName;
    String region3depthName;
    String phoneNumber;
    String intro;
    LocalTime openTime;
    LocalTime closeTime;
    String registrationNumber;
    Long deliveryWaitTime;
    Long deliveryTip;
    Boolean packageAvailable;
    Long packageWaitTime;

    Float reviewScore;
    Long reviewCount;
    Long orderCount;

    Long minPrice;

    public StoreInfoDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.region1depthName = store.getRegion1depthName();
        this.region2depthName = store.getRegion2depthName();
        this.region3depthName = store.getRegion3depthName();
        this.phoneNumber = store.getPhoneNumber();
        this.intro = store.getIntro();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.registrationNumber = store.getRegistrationNumber();
        this.deliveryWaitTime = store.getDeliveryWaitTime();
        this.deliveryTip = store.getDeliveryTip();
        this.packageAvailable = store.getPackageAvailable();
        this.packageWaitTime = store.getPackageWaitTime();
        this.reviewScore = store.getReviewScore();
        this.reviewCount = store.getReviewCount();
        this.orderCount = store.getOrderCount();
        this.minPrice = store.getMinPrice();
    }
}
