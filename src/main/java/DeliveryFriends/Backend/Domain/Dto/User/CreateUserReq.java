package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserReq {
    String name;
    String nickname;
    String kakaoId;
}
