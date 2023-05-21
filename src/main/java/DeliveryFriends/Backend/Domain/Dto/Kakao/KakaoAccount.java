package DeliveryFriends.Backend.Domain.Dto.Kakao;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class KakaoAccount {
    Boolean profile_needs_agreement;
    Boolean profile_nickname_needs_agreement;
    Boolean profile_image_needs_agreement;
    KakaoProfile profile;
    Boolean name_needs_agreement;
    String name;
    Boolean email_needs_agreement;
    Boolean has_email;
    Boolean is_email_valid;
    Boolean is_email_verified;
    String email;
    Boolean has_phone_number;
    Boolean phone_number_needs_agreement;
    String phone_number;
}