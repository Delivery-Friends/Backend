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

    Long teamId;
    Long storeId;
    String storeName;
    Long leaderId;
    String leaderName;
    String leaderImgSrc;
    String category;
    List<String> storeImgUrl;
    Float storeScore;
    Long reviewCount;
    Long deliveryTime;
    Long deliveryTip;
    Long minPrice;

    Long maxMember;
    Long nowMember;

    String basicAddress;
    String detailedAddress;

    String latitude;
    String longitude;

    public TeamRes(LocalDateTime groupEndTime, Long teamId, Long storeId, String storeName, Long leaderId, String leaderName, String leaderImgSrc, String category, List<String> storeImgUrl, Float storeScore, Long reviewCount, Long deliveryTime, Long deliveryTip, Long minPrice, Long maxMember, Long nowMember, String basicAddress, String detailedAddress, String latitude, String longitude) {
        this.groupEndTime = groupEndTime;
        this.teamId = teamId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.leaderId = leaderId;
        this.leaderName = leaderName;
        this.leaderImgSrc = leaderImgSrc;
        this.category = category;
        this.storeImgUrl = storeImgUrl;
        this.storeScore = storeScore;
        this.reviewCount = reviewCount;
        this.deliveryTime = deliveryTime;
        this.deliveryTip = deliveryTip;
        this.minPrice = minPrice;
        this.maxMember = maxMember;
        this.nowMember = nowMember;
        this.basicAddress = basicAddress;
        this.detailedAddress = detailedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
