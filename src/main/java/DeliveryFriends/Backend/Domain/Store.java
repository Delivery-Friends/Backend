package DeliveryFriends.Backend.Domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
@Getter
public class Store extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String region1depthName;
    String region2depthName;
    String region3depth;
    String phoneNumber;
    String intro;
    LocalTime openTime;
    LocalTime closeTime;
    String registrationNumber;
    Long deliveryWaitTime;
    Long deliveryTip;
    Boolean packageAvailable;
    Long packageWaitTime;
}
