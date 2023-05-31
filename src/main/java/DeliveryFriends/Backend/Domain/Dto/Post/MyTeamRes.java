package DeliveryFriends.Backend.Domain.Dto.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MyTeamRes {
    Long storeId;
    String storeName;
    float storeScore;
    Long reviewCount;
    String basicAddress;
    String detailedAddress;

    String longitude;
    String latitude;

    LocalDateTime endTime;
    List<String> medium;

    public MyTeamRes(Long storeId, String storeName, float storeScore, Long reviewCount, String basicAddress, String detailedAddress, String longitude, String latitude, LocalDateTime endTime, List<String> medium) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeScore = storeScore;
        this.reviewCount = reviewCount;
        this.basicAddress = basicAddress;
        this.detailedAddress = detailedAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.endTime = endTime;
        this.medium = medium;
    }
}