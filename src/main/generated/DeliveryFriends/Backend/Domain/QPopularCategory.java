package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPopularCategory is a Querydsl query type for PopularCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPopularCategory extends EntityPathBase<PopularCategory> {

    private static final long serialVersionUID = 1230446388L;

    public static final QPopularCategory popularCategory = new QPopularCategory("popularCategory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath category = createString("category");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPopularCategory(String variable) {
        super(PopularCategory.class, forVariable(variable));
    }

    public QPopularCategory(Path<? extends PopularCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPopularCategory(PathMetadata metadata) {
        super(PopularCategory.class, metadata);
    }

}

