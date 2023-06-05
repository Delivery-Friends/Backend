package DeliveryFriends.Backend.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPay is a Querydsl query type for Pay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPay extends EntityPathBase<Pay> {

    private static final long serialVersionUID = 663574629L;

    public static final QPay pay = new QPay("pay");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath account = createString("account");

    public final StringPath cardNumber = createString("cardNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath paymentMethod = createString("paymentMethod");

    public final StringPath payStatus = createString("payStatus");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final StringPath telNumber = createString("telNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPay(String variable) {
        super(Pay.class, forVariable(variable));
    }

    public QPay(Path<? extends Pay> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPay(PathMetadata metadata) {
        super(Pay.class, metadata);
    }

}

