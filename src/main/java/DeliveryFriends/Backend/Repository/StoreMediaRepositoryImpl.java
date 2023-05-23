package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Dto.FilenameDto;
import DeliveryFriends.Backend.Domain.Dto.QFilenameDto;
import DeliveryFriends.Backend.Domain.QStoreMedia;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static DeliveryFriends.Backend.Domain.QStoreMedia.*;

@RequiredArgsConstructor
public class StoreMediaRepositoryImpl implements StoreMediaRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FilenameDto> getStoreMedium(Long storeId) {
        return queryFactory
                .select(new QFilenameDto(storeMedia.fileName))
                .from(storeMedia)
                .where(storeMedia.store.id.eq(storeId)
                        .and(storeMedia.deleted.eq(false)
                        )
                )
                .fetch();
    }
}
