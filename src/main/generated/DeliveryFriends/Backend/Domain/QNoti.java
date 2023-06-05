package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoti is a Querydsl query type for Noti
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoti extends EntityPathBase<Noti> {

    private static final long serialVersionUID = -904069159L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoti noti = new QNoti("noti");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QNoti(String variable) {
        this(Noti.class, forVariable(variable), INITS);
    }

    public QNoti(Path<? extends Noti> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoti(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoti(PathMetadata metadata, PathInits inits) {
        this(Noti.class, metadata, inits);
    }

    public QNoti(Class<? extends Noti> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

