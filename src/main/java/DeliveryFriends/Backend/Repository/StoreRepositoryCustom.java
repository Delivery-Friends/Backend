package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Dto.Store.SimpleStoreDto;
import DeliveryFriends.Backend.Domain.Dto.Store.StoreCondDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreRepositoryCustom {
    List<SimpleStoreDto> getStoreList(Pageable pageable, StoreCondDto cond);
}
