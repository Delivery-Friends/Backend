package DeliveryFriends.Backend.Domain.Dto.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamStatusDto {
    String myStatus;
    List<TeamOrderStatusDto> teamOrderStatus;
}
