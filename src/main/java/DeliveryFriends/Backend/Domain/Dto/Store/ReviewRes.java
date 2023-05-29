package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ReviewRes {
    String nickname;
    String imgSrc;
    Long score;
    String content;
    LocalDateTime createdAt;
    List<String> reviewMedium;

    public ReviewRes(String nickname, String imgSrc, Long score, String content, LocalDateTime createdAt, List<String> reviewMedium) {
        this.nickname = nickname;
        this.imgSrc = imgSrc;
        this.score = score;
        this.content = content;
        this.createdAt = createdAt;
        this.reviewMedium = reviewMedium;
    }
}
