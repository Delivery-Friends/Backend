package DeliveryFriends.Backend.Domain.Dto.Store;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * DeliveryFriends.Backend.Domain.Dto.Store.QSimpleStoreDto is a Querydsl Projection type for SimpleStoreDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSimpleStoreDto extends ConstructorExpression<SimpleStoreDto> {

    private static final long serialVersionUID = -932312305L;

    public QSimpleStoreDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Long> deliveryWaitTime, com.querydsl.core.types.Expression<Boolean> packageAvailable, com.querydsl.core.types.Expression<Long> packageWaitTime, com.querydsl.core.types.Expression<Long> deliveryTip, com.querydsl.core.types.Expression<Long> reviewScore, com.querydsl.core.types.Expression<Long> minPrice, com.querydsl.core.types.Expression<Long> reviewCount) {
        super(SimpleStoreDto.class, new Class<?>[]{long.class, String.class, long.class, boolean.class, long.class, long.class, long.class, long.class, long.class}, id, name, deliveryWaitTime, packageAvailable, packageWaitTime, deliveryTip, reviewScore, minPrice, reviewCount);
    }

}

