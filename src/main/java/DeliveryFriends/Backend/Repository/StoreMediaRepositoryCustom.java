package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Dto.FilenameDto;

import java.util.List;

public interface StoreMediaRepositoryCustom {
    public List<FilenameDto> getStoreMedium(Long storeId);
}
