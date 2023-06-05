package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenuOptionGroup is a Querydsl query type for MenuOptionGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuOptionGroup extends EntityPathBase<MenuOptionGroup> {

    private static final long serialVersionUID = 1797801064L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenuOptionGroup menuOptionGroup = new QMenuOptionGroup("menuOptionGroup");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMenu menu;

    public final NumberPath<Long> multiSelect = createNumber("multiSelect", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMenuOptionGroup(String variable) {
        this(MenuOptionGroup.class, forVariable(variable), INITS);
    }

    public QMenuOptionGroup(Path<? extends MenuOptionGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenuOptionGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenuOptionGroup(PathMetadata metadata, PathInits inits) {
        this(MenuOptionGroup.class, metadata, inits);
    }

    public QMenuOptionGroup(Class<? extends MenuOptionGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menu = inits.isInitialized("menu") ? new QMenu(forProperty("menu"), inits.get("menu")) : null;
    }

}

