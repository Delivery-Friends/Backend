package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeUserRes {
    Long userId;
    String nickname;
    String name;
    String ImgSrc;
    Float score;
    Long reviewCount;
}
