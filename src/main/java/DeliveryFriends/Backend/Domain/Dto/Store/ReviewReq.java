package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewReq {
    Long orderId;
    Long score;
    String content;
    List<String> media;
}
