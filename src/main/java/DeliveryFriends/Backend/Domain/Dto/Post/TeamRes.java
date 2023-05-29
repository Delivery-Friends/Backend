package DeliveryFriends.Backend.Domain.Dto.Post;

import DeliveryFriends.Backend.Domain.Store;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

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
}
