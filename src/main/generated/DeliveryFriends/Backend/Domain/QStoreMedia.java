package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreMedia is a Querydsl query type for StoreMedia
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreMedia extends EntityPathBase<StoreMedia> {

    private static final long serialVersionUID = 562529574L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreMedia storeMedia = new QStoreMedia("storeMedia");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QStore store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QStoreMedia(String variable) {
        this(StoreMedia.class, forVariable(variable), INITS);
    }

    public QStoreMedia(Path<? extends StoreMedia> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreMedia(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreMedia(PathMetadata metadata, PathInits inits) {
        this(StoreMedia.class, metadata, inits);
    }

    public QStoreMedia(Class<? extends StoreMedia> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store")) : null;
    }

}

