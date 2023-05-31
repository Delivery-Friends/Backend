package DeliveryFriends.Backend.Domain.Dto.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamOrderStatusDto {
    String nickname;
    String status;

    public TeamOrderStatusDto(String nickname, String status) {
        this.nickname = nickname;
        this.status = status;
    }
}
