package DeliveryFriends.Backend.Domain.Dto.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostListReq {
    String lessLatitude;
    String greaterLatitude;
    String lessLongitude;
    String greaterLongitude;
}
