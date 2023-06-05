package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChoiceOption is a Querydsl query type for ChoiceOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChoiceOption extends EntityPathBase<ChoiceOption> {

    private static final long serialVersionUID = 846664281L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChoiceOption choiceOption = new QChoiceOption("choiceOption");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QChoiceMenu choiceMenu;

    public final NumberPath<Long> count = createNumber("count", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMenuOption menuOption;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QChoiceOption(String variable) {
        this(ChoiceOption.class, forVariable(variable), INITS);
    }

    public QChoiceOption(Path<? extends ChoiceOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChoiceOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChoiceOption(PathMetadata metadata, PathInits inits) {
        this(ChoiceOption.class, metadata, inits);
    }

    public QChoiceOption(Class<? extends ChoiceOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.choiceMenu = inits.isInitialized("choiceMenu") ? new QChoiceMenu(forProperty("choiceMenu"), inits.get("choiceMenu")) : null;
        this.menuOption = inits.isInitialized("menuOption") ? new QMenuOption(forProperty("menuOption"), inits.get("menuOption")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

