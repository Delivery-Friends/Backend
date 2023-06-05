package DeliveryFriends.Backend.Domain.Dto.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TeamListRes {
    LocalDateTime groupEndTime;

    Long teamId;
    Long storeId;
    String storeName;
    String leaderName;
    String category;
    List<String> storeImgUrl;
    Float storeScore;
    Long reviewCount;

    Long maxMember;
    Long nowMember;

    String basicAddress;

    String latitude;
    String longitude;

    public TeamListRes(LocalDateTime groupEndTime, Long teamId, Long storeId, String storeName, String leaderName, String category, List<String> storeImgUrl, Float storeScore, Long reviewCount, Long maxMember, Long nowMember, String basicAddress, String latitude, String longitude) {
        this.groupEndTime = groupEndTime;
        this.teamId = teamId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.leaderName = leaderName;
        this.category = category;
        this.storeImgUrl = storeImgUrl;
        this.storeScore = storeScore;
        this.reviewCount = reviewCount;
        this.maxMember = maxMember;
        this.nowMember = nowMember;
        this.basicAddress = basicAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
