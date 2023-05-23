package DeliveryFriends.Backend.Domain.Dto.Store;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReadMenuDto {

    Long id;
    String name;
    Long price;
    String expression;

    List<ReadMenuOptionGroup> readMenuOptionGroupList;

    public ReadMenuDto(String name, Long price, String expression, List<ReadMenuOptionGroup> readMenuOptionGroupList) {
        this.name = name;
        this.price = price;
        this.expression = expression;
        this.readMenuOptionGroupList = readMenuOptionGroupList;
    }
}
