package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserReq {
    String name;
    String nickname;
    LocalDate birth;
    String email;
    String password;

    String region1depth;
    String region2depth;
    String region3depth;
    String kakaoId;
    String gender;
}
