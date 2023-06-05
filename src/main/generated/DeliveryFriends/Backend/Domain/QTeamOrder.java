package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamOrder is a Querydsl query type for TeamOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamOrder extends EntityPathBase<TeamOrder> {

    private static final long serialVersionUID = 1183870190L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamOrder teamOrder = new QTeamOrder("teamOrder");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCart cart;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isLeader = createBoolean("isLeader");

    public final StringPath orderStatus = createString("orderStatus");

    public final QTeam team;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QTeamOrder(String variable) {
        this(TeamOrder.class, forVariable(variable), INITS);
    }

    public QTeamOrder(Path<? extends TeamOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamOrder(PathMetadata metadata, PathInits inits) {
        this(TeamOrder.class, metadata, inits);
    }

    public QTeamOrder(Class<? extends TeamOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new QCart(forProperty("cart"), inits.get("cart")) : null;
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team"), inits.get("team")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

