package DeliveryFriends.Backend.Repository;

import DeliveryFriends.Backend.Domain.Dto.Store.QSimpleStoreDto;
import DeliveryFriends.Backend.Domain.Dto.Store.SimpleStoreDto;
import DeliveryFriends.Backend.Domain.Dto.Store.StoreCondDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static DeliveryFriends.Backend.Domain.QMenu.*;
import static DeliveryFriends.Backend.Domain.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SimpleStoreDto> getStoreList(Pageable pageable, StoreCondDto cond) {
        JPAQuery<SimpleStoreDto> query = queryFactory.selectDistinct(new QSimpleStoreDto(
                store.id,
                store.name,
                store.deliveryWaitTime,
                store.packageAvailable,
                store.packageWaitTime,
                store.deliveryTip,
                store.reviewScore,
                store.minPrice
        ))
                .from(menu)
                .where(
                        store.deleted.eq(false),
                        searchFilter(cond.getSearch()),
                        region1Filter(cond.getRegion1depthName()),
                        region2Filter(cond.getRegion2depthName()),
                        categoryFilter(cond.getCategory()),
                        store.openTime.loe(LocalDateTime.now().toLocalTime()),
                        store.closeTime.goe(LocalDateTime.now().toLocalTime())
                )
                .innerJoin(menu.store, store)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(store.getType(), store.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        List<SimpleStoreDto> result = query.fetch();
        return result;
    }

    private BooleanExpression searchFilter(String search) {
        if (StringUtils.hasText(search)) {
            return store.name.like("%" + search + "%").or(menu.name.like("%" + search + "%"));
        }
        return null;
    }

    private BooleanExpression region1Filter(String region1Name) {
        if (StringUtils.hasText(region1Name)) {
            return store.region1depthName.eq(region1Name);
        }
        return null;
    }

    private BooleanExpression region2Filter(String region2Name) {
        if (StringUtils.hasText(region2Name)) {
            return store.region2depthName.eq(region2Name);
        }
        return null;
    }

    private BooleanExpression categoryFilter(String category) {
        if (StringUtils.hasText(category)) {
            return store.category.like("%" + category + "%");
        }
        return null;
    }
}
