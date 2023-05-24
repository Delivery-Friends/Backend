package DeliveryFriends.Backend.Domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String nickname;
    LocalDate birth;
    String email;
    String password;
    String kakaoId;
    String gender;
    Long point;
    String refreshToken;

    @JoinColumn(name = "team_id")
    @ManyToOne
    Team team;

    @Builder
    public User(String name, String nickname, LocalDate birth, String email, String password, String kakaoId, String gender, Long point) {
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.email = email;
        this.password = password;
        this.kakaoId = kakaoId;
        this.gender = gender;
        this.point = point;
    }
}
