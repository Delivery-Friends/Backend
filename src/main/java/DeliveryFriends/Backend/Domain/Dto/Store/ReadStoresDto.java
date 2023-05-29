package DeliveryFriends.Backend.Domain.Dto.Store;

import DeliveryFriends.Backend.Domain.Dto.FilenameDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReadStoresDto {
    Long id;

    String name;
    Long deliveryWaitTime;
    Boolean packageAvailable;
    Long packageWaitTime;
    Long deliveryTip;
    Float reviewScore;
    Long minPrice;
    List<FilenameDto> fileNames;

    public ReadStoresDto(Long id, String name, Long deliveryWaitTime, Boolean packageAvailable, Long packageWaitTime, Long deliveryTip, Float reviewScore, Long minPrice, List<FilenameDto> fileNames) {
        this.id = id;
        this.name = name;
        this.deliveryWaitTime = deliveryWaitTime;
        this.packageAvailable = packageAvailable;
        this.packageWaitTime = packageWaitTime;
        this.deliveryTip = deliveryTip;
        this.reviewScore = reviewScore;
        this.minPrice = minPrice;
        this.fileNames = fileNames;
    }
}
