package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenuOption is a Querydsl query type for MenuOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuOption extends EntityPathBase<MenuOption> {

    private static final long serialVersionUID = -888709033L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenuOption menuOption = new QMenuOption("menuOption");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> defaultValue = createNumber("defaultValue", Long.class);

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> maxCount = createNumber("maxCount", Long.class);

    public final QMenuOptionGroup menuOptionGroup;

    public final StringPath name = createString("name");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMenuOption(String variable) {
        this(MenuOption.class, forVariable(variable), INITS);
    }

    public QMenuOption(Path<? extends MenuOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenuOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenuOption(PathMetadata metadata, PathInits inits) {
        this(MenuOption.class, metadata, inits);
    }

    public QMenuOption(Class<? extends MenuOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menuOptionGroup = inits.isInitialized("menuOptionGroup") ? new QMenuOptionGroup(forProperty("menuOptionGroup"), inits.get("menuOptionGroup")) : null;
    }

}

