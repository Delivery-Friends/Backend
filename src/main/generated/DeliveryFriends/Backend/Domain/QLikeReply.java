package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeReply is a Querydsl query type for LikeReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeReply extends EntityPathBase<LikeReply> {

    private static final long serialVersionUID = 1198374800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeReply likeReply = new QLikeReply("likeReply");

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

    public QLikeReply(String variable) {
        this(LikeReply.class, forVariable(variable), INITS);
    }

    public QLikeReply(Path<? extends LikeReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeReply(PathMetadata metadata, PathInits inits) {
        this(LikeReply.class, metadata, inits);
    }

    public QLikeReply(Class<? extends LikeReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postReply = inits.isInitialized("postReply") ? new QPostReply(forProperty("postReply"), inits.get("postReply")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

