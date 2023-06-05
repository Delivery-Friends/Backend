package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDislikePost is a Querydsl query type for DislikePost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDislikePost extends EntityPathBase<DislikePost> {

    private static final long serialVersionUID = -1083782622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDislikePost dislikePost = new QDislikePost("dislikePost");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QDislikePost(String variable) {
        this(DislikePost.class, forVariable(variable), INITS);
    }

    public QDislikePost(Path<? extends DislikePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDislikePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDislikePost(PathMetadata metadata, PathInits inits) {
        this(DislikePost.class, metadata, inits);
    }

    public QDislikePost(Class<? extends DislikePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

