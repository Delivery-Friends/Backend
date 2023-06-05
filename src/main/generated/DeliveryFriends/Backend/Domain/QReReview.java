package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReReview is a Querydsl query type for ReReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReReview extends EntityPathBase<ReReview> {

    private static final long serialVersionUID = -973609522L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReReview reReview = new QReReview("reReview");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReReview(String variable) {
        this(ReReview.class, forVariable(variable), INITS);
    }

    public QReReview(Path<? extends ReReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReReview(PathMetadata metadata, PathInits inits) {
        this(ReReview.class, metadata, inits);
    }

    public QReReview(Class<? extends ReReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

