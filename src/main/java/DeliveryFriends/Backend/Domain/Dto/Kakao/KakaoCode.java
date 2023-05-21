package DeliveryFriends.Backend.Domain.Dto.Kakao;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class KakaoCode {
    String code;
    String state;
    String error;
    String error_description;
}
