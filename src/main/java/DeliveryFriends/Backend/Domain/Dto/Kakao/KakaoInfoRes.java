package DeliveryFriends.Backend.Domain.Dto.Kakao;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class KakaoInfoRes {
    String id;
    String connected_at;
    KakaoProperties properties;
    KakaoAccount kakao_account;
}
