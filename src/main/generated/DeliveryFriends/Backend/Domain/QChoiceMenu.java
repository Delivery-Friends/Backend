package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChoiceMenu is a Querydsl query type for ChoiceMenu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChoiceMenu extends EntityPathBase<ChoiceMenu> {

    private static final long serialVersionUID = 192989251L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChoiceMenu choiceMenu = new QChoiceMenu("choiceMenu");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCart cart;

    public final NumberPath<Long> count = createNumber("count", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMenu menu;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QChoiceMenu(String variable) {
        this(ChoiceMenu.class, forVariable(variable), INITS);
    }

    public QChoiceMenu(Path<? extends ChoiceMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChoiceMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChoiceMenu(PathMetadata metadata, PathInits inits) {
        this(ChoiceMenu.class, metadata, inits);
    }

    public QChoiceMenu(Class<? extends ChoiceMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new QCart(forProperty("cart"), inits.get("cart")) : null;
        this.menu = inits.isInitialized("menu") ? new QMenu(forProperty("menu"), inits.get("menu")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

