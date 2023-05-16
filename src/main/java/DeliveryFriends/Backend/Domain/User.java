package DeliveryFriends.Backend.Domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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
    String point;

    @JoinColumn(name = "group_id")
    @ManyToOne
    Group group;
}
