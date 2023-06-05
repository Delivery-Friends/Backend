package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewMedia is a Querydsl query type for ReviewMedia
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewMedia extends EntityPathBase<ReviewMedia> {

    private static final long serialVersionUID = -679151063L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewMedia reviewMedia = new QReviewMedia("reviewMedia");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReviewMedia(String variable) {
        this(ReviewMedia.class, forVariable(variable), INITS);
    }

    public QReviewMedia(Path<? extends ReviewMedia> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewMedia(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewMedia(PathMetadata metadata, PathInits inits) {
        this(ReviewMedia.class, metadata, inits);
    }

    public QReviewMedia(Class<? extends ReviewMedia> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

