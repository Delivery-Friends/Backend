package DeliveryFriends.Backend.Domain.Dto.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinPostReq {
    Long teamId;
    Long cartId;
}
