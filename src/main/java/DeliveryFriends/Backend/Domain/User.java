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
    String kakaoId;
    Long point;
    String refreshToken;
    String imgSrc;
    String password;

    String role;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Team team;

    @Builder
    public User(String name, String nickname, String kakaoId, Long point, String imgSrc, String role, String password) {
        this.name = name;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.point = point;
        this.imgSrc = imgSrc;
        this.role = role;
        this.password = password;
    }
}
