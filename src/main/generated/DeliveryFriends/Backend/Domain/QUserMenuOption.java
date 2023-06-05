package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserMenuOption is a Querydsl query type for UserMenuOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserMenuOption extends EntityPathBase<UserMenuOption> {

    private static final long serialVersionUID = -519791358L;

    public static final QUserMenuOption userMenuOption = new QUserMenuOption("userMenuOption");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QUserMenuOption(String variable) {
        super(UserMenuOption.class, forVariable(variable));
    }

    public QUserMenuOption(Path<? extends UserMenuOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserMenuOption(PathMetadata metadata) {
        super(UserMenuOption.class, metadata);
    }

}

