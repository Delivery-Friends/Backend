package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime groupEndTime;

    @JoinColumn(name = "store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Store store;

    String leaderName;

    @Column(columnDefinition = "TEXT")
    String leaderImgSrc;

    Long leaderId;

    Long maxMember;

    String basicAddress;
    String detailedAddress;

    String latitude;
    String longitude;

    Long divideTip;

    String orderStatus;

    public Team(LocalDateTime groupEndTime, Store store, String leaderName, String leaderImgSrc, Long leaderId, Long maxMember, String basicAddress, String detailedAddress, String latitude, String longitude, Long divideTip, String orderStatus) {
        this.groupEndTime = groupEndTime;
        this.store = store;
        this.leaderName = leaderName;
        this.leaderImgSrc = leaderImgSrc;
        this.leaderId = leaderId;
        this.maxMember = maxMember;
        this.basicAddress = basicAddress;
        this.detailedAddress = detailedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.divideTip = divideTip;
        this.orderStatus = orderStatus;
    }
}
