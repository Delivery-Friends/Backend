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
    List<FilenameDto> fileNames;

    public ReadStoresDto(SimpleStoreDto simpleStoreDto, List<FilenameDto> fileNames) {
        this.id = simpleStoreDto.getId();
        this.name = simpleStoreDto.getName();
        this.deliveryWaitTime = simpleStoreDto.getDeliveryWaitTime();
        this.packageAvailable = simpleStoreDto.getPackageAvailable();
        this.packageWaitTime = simpleStoreDto.getPackageWaitTime();
        this.deliveryTip = simpleStoreDto.getDeliveryTip();
        this.fileNames = fileNames;
    }
}
