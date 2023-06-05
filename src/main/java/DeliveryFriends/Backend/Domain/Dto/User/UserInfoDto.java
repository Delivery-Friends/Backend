package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {
    String nickname;
    String imgSrc;
    Float score;
    Long reviewCount;
    Long likeCount;
    Boolean isLike;
}
