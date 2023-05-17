package DeliveryFriends.Backend.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
@Data
public class Store extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String region1depth;
    String region2depth;
    String region3depth;
    String phoneNumber;
    String intro;
    LocalTime openTime;
    LocalTime closeTime;
    String registrationNumber;
    Long deliveryWaitTime;
    Boolean packageAvailable;
    Long packageWaitTime;
}
