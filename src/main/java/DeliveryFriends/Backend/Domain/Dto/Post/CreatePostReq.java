package DeliveryFriends.Backend.Domain.Dto.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreatePostReq {
    Long cartId;
    LocalDateTime endTime;
    Long storeId;
    Long maxMember;

    String basicAddress;
    String detailedAddress;

    String latitude;
    String logitude;
}
