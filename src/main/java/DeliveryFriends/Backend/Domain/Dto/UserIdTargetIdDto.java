package DeliveryFriends.Backend.Domain.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserIdTargetIdDto {
    Long userId;
    Long targetId;

    public UserIdTargetIdDto(Long userId, Long targetId) {
        this.userId = userId;
        this.targetId = targetId;
    }
}
