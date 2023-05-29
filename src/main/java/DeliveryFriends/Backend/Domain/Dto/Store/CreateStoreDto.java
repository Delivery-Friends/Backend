package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class CreateStoreDto {
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
    String category;

    Long minPrice;
}
