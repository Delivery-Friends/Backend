package DeliveryFriends.Backend.Domain.Dto.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserReviewRes {
    Long userId;
    String nickname;
    String imgSrc;
    String content;
    Long score;
    LocalDateTime createdDate;
}
