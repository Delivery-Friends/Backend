package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime groupEndTime;

    @JoinColumn(name = "store_id")
    @ManyToOne
    Store store;

    Long maxMember;

    String basicAddress;
    String detailedAddress;

    String latitude;
    String longitude;

    public Team(LocalDateTime groupEndTime, Store store, Long maxMember, String basicAddress, String detailedAddress, String latitude, String longitude) {
        this.groupEndTime = groupEndTime;
        this.store = store;
        this.maxMember = maxMember;
        this.basicAddress = basicAddress;
        this.detailedAddress = detailedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
