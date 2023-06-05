package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDislikeReply is a Querydsl query type for DislikeReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDislikeReply extends EntityPathBase<DislikeReply> {

    private static final long serialVersionUID = 764023208L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDislikeReply dislikeReply = new QDislikeReply("dislikeReply");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPostReply postReply;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QDislikeReply(String variable) {
        this(DislikeReply.class, forVariable(variable), INITS);
    }

    public QDislikeReply(Path<? extends DislikeReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDislikeReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDislikeReply(PathMetadata metadata, PathInits inits) {
        this(DislikeReply.class, metadata, inits);
    }

    public QDislikeReply(Class<? extends DislikeReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postReply = inits.isInitialized("postReply") ? new QPostReply(forProperty("postReply"), inits.get("postReply")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

