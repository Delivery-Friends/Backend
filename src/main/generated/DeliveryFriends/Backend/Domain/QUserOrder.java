package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserOrder is a Querydsl query type for UserOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOrder extends EntityPathBase<UserOrder> {

    private static final long serialVersionUID = 1699117120L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserOrder userOrder = new QUserOrder("userOrder");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> deliveryTip = createNumber("deliveryTip", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath menuInfo = createString("menuInfo");

    public final StringPath orderInfo = createString("orderInfo");

    public final StringPath paymentKey = createString("paymentKey");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final QStore store;

    public final QTeam team;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QUserOrder(String variable) {
        this(UserOrder.class, forVariable(variable), INITS);
    }

    public QUserOrder(Path<? extends UserOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserOrder(PathMetadata metadata, PathInits inits) {
        this(UserOrder.class, metadata, inits);
    }

    public QUserOrder(Class<? extends UserOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store")) : null;
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team"), inits.get("team")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

