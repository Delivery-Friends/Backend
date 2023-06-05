package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserReview is a Querydsl query type for UserReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserReview extends EntityPathBase<UserReview> {

    private static final long serialVersionUID = 1207444646L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserReview userReview = new QUserReview("userReview");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath context = createString("context");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> score = createNumber("score", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public final QUser writer;

    public QUserReview(String variable) {
        this(UserReview.class, forVariable(variable), INITS);
    }

    public QUserReview(Path<? extends UserReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserReview(PathMetadata metadata, PathInits inits) {
        this(UserReview.class, metadata, inits);
    }

    public QUserReview(Class<? extends UserReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
        this.writer = inits.isInitialized("writer") ? new QUser(forProperty("writer"), inits.get("writer")) : null;
    }

}

