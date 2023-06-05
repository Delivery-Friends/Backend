package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 2043389278L;

    public static final QStore store = new QStore("store");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath category = createString("category");

    public final TimePath<java.time.LocalTime> closeTime = createTime("closeTime", java.time.LocalTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> deliveryTip = createNumber("deliveryTip", Long.class);

    public final NumberPath<Long> deliveryWaitTime = createNumber("deliveryWaitTime", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final NumberPath<Long> minPrice = createNumber("minPrice", Long.class);

    public final StringPath name = createString("name");

    public final TimePath<java.time.LocalTime> openTime = createTime("openTime", java.time.LocalTime.class);

    public final NumberPath<Long> orderCount = createNumber("orderCount", Long.class);

    public final BooleanPath packageAvailable = createBoolean("packageAvailable");

    public final NumberPath<Long> packageWaitTime = createNumber("packageWaitTime", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath region1depthName = createString("region1depthName");

    public final StringPath region2depthName = createString("region2depthName");

    public final StringPath region3depthName = createString("region3depthName");

    public final StringPath registrationNumber = createString("registrationNumber");

    public final NumberPath<Long> reviewCount = createNumber("reviewCount", Long.class);

    public final NumberPath<Long> reviewScore = createNumber("reviewScore", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QStore(String variable) {
        super(Store.class, forVariable(variable));
    }

    public QStore(Path<? extends Store> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStore(PathMetadata metadata) {
        super(Store.class, metadata);
    }

}

