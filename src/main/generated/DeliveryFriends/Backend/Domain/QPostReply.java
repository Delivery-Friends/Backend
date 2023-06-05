package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostReply is a Querydsl query type for PostReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostReply extends EntityPathBase<PostReply> {

    private static final long serialVersionUID = -930016217L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostReply postReply = new QPostReply("postReply");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QPostReply(String variable) {
        this(PostReply.class, forVariable(variable), INITS);
    }

    public QPostReply(Path<? extends PostReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostReply(PathMetadata metadata, PathInits inits) {
        this(PostReply.class, metadata, inits);
    }

    public QPostReply(Class<? extends PostReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

