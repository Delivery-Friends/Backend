package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoIdDto {
    String kakaoId;

    public KakaoIdDto(String kakaoId) {
        this.kakaoId = kakaoId;
    }
}
