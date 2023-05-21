package DeliveryFriends.Backend.Domain.Dto.Kakao;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class KakaoToken {
    String access_token;
    String refresh_token;
    String token_type;
    String expires_in;
    String scope;
    String refresh_token_expires_in;
}
