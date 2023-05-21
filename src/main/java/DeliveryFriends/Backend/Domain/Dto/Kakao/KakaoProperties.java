package DeliveryFriends.Backend.Domain.Dto.Kakao;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class KakaoProperties {
    String nickname;
    String profile_image;
    String thumbnail_image;
}