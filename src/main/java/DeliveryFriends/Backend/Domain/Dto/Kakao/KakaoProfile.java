package DeliveryFriends.Backend.Domain.Dto.Kakao;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class KakaoProfile {
    String nickname;
    String thumbnail_image_url;
    String profile_image_url;
    Boolean is_default_image;
}