package DeliveryFriends.Backend.Domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PopularMenu extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String category;

    String uuid;
}
