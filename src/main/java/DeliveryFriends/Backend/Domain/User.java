package DeliveryFriends.Backend.Domain;

import lombok.Builder;
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
    Long point;
    String refreshToken;

    @JoinColumn(name = "team_id")
    @ManyToOne
    Team team;

    @Builder
    public User(String name, String nickname, LocalDate birth, String email, String password, String region1depth, String region2depth, String region3depth, String kakaoId, String gender, Long point) {
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.email = email;
        this.password = password;
        this.region1depth = region1depth;
        this.region2depth = region2depth;
        this.region3depth = region3depth;
        this.kakaoId = kakaoId;
        this.gender = gender;
        this.point = point;
    }
}
