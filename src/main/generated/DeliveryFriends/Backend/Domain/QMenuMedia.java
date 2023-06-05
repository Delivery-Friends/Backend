package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenuMedia is a Querydsl query type for MenuMedia
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuMedia extends EntityPathBase<MenuMedia> {

    private static final long serialVersionUID = -169405502L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenuMedia menuMedia = new QMenuMedia("menuMedia");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMenu menu;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMenuMedia(String variable) {
        this(MenuMedia.class, forVariable(variable), INITS);
    }

    public QMenuMedia(Path<? extends MenuMedia> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenuMedia(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenuMedia(PathMetadata metadata, PathInits inits) {
        this(MenuMedia.class, metadata, inits);
    }

    public QMenuMedia(Class<? extends MenuMedia> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menu = inits.isInitialized("menu") ? new QMenu(forProperty("menu"), inits.get("menu")) : null;
    }

}

