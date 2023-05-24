package DeliveryFriends.Backend.Domain;

import DeliveryFriends.Backend.Domain.Dto.Store.CreateStoreDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class Store extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String region1depthName;
    String region2depthName;
    String region3depth;
    String phoneNumber;
    String intro;
    LocalTime openTime;
    LocalTime closeTime;
    String registrationNumber;
    Long deliveryWaitTime;
    Long deliveryTip;
    Boolean packageAvailable;
    Long packageWaitTime;

    Long reviewCount;
    Long orderCount;

    @Builder
    public Store(String name, String region1depthName, String region2depthName, String region3depth, String phoneNumber, String intro, LocalTime openTime, LocalTime closeTime, String registrationNumber, Long deliveryWaitTime, Long deliveryTip, Boolean packageAvailable, Long packageWaitTime, Long reviewCount, Long orderCount) {
        this.name = name;
        this.region1depthName = region1depthName;
        this.region2depthName = region2depthName;
        this.region3depth = region3depth;
        this.phoneNumber = phoneNumber;
        this.intro = intro;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.registrationNumber = registrationNumber;
        this.deliveryWaitTime = deliveryWaitTime;
        this.deliveryTip = deliveryTip;
        this.packageAvailable = packageAvailable;
        this.packageWaitTime = packageWaitTime;
        this.reviewCount = reviewCount;
        this.orderCount = orderCount;
    }

    public Store(CreateStoreDto createStoreDto, Long reviewCount, Long orderCount) {
        this.name = createStoreDto.getName();
        this.region1depthName = createStoreDto.getRegion1depthName();
        this.region2depthName = createStoreDto.getRegion2depthName();
        this.region3depth = createStoreDto.getRegion3depthName();
        this.phoneNumber = createStoreDto.getPhoneNumber();
        this.intro = createStoreDto.getIntro();
        this.openTime = createStoreDto.getOpenTime();
        this.closeTime = createStoreDto.getCloseTime();
        this.registrationNumber = createStoreDto.getRegistrationNumber();
        this.deliveryWaitTime = createStoreDto.getDeliveryWaitTime();
        this.deliveryTip = createStoreDto.getDeliveryTip();
        this.packageAvailable = createStoreDto.getPackageAvailable();
        this.packageWaitTime = createStoreDto.getPackageWaitTime();
        this.reviewCount = reviewCount;
        this.orderCount = orderCount;
    }


}
