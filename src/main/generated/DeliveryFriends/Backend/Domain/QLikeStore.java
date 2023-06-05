package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeStore is a Querydsl query type for LikeStore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeStore extends EntityPathBase<LikeStore> {

    private static final long serialVersionUID = 1199744391L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeStore likeStore = new QLikeStore("likeStore");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QStore store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QLikeStore(String variable) {
        this(LikeStore.class, forVariable(variable), INITS);
    }

    public QLikeStore(Path<? extends LikeStore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeStore(PathMetadata metadata, PathInits inits) {
        this(LikeStore.class, metadata, inits);
    }

    public QLikeStore(Class<? extends LikeStore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

