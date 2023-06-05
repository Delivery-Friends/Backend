package DeliveryFriends.Backend.Domain.Dto.Post;

import DeliveryFriends.Backend.Domain.Team;
import DeliveryFriends.Backend.Domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamAndMembersDto {
    Team team;
    List<User> members;

    public TeamAndMembersDto(Team team, List<User> members) {
        this.team = team;
        this.members = members;
    }
}
