package DeliveryFriends.Backend.Domain.Dto.Post;

import DeliveryFriends.Backend.Domain.Store;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TeamRes {
    LocalDateTime groupEndTime;

    Long storeId;
    String storeName;
    List<String> storeImgUrl;
    Float storeScore;
    Long reviewCount;
    Long deliveryTime;
    Long deliveryTip;
    Long minPrice;

    Long maxMember;

    String basicAddress;
    String detailedAddress;

    String latitude;
    String longitude;

    public TeamRes(LocalDateTime groupEndTime, Long storeId, String storeName, List<String> storeImgUrl, Float storeScore, Long reviewCount, Long deliveryTime, Long deliveryTip, Long minPrice, Long maxMember, String basicAddress, String detailedAddress, String latitude, String longitude) {

        this.groupEndTime = groupEndTime;
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeImgUrl = storeImgUrl;
        this.storeScore = storeScore;
        this.reviewCount = reviewCount;
        this.deliveryTime = deliveryTime;
        this.deliveryTip = deliveryTip;
        this.minPrice = minPrice;
        this.maxMember = maxMember;
        this.basicAddress = basicAddress;
        this.detailedAddress = detailedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
