package DeliveryFriends.Backend.Domain.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PopularCategoryDto implements Comparable<PopularCategoryDto> {
    String category;
    Long count;

    public PopularCategoryDto(String category, Long count) {
        this.category = category;
        this.count = count;
    }

    @Override
    public int compareTo(PopularCategoryDto popularCategoryDto) {
        if (popularCategoryDto.count <= count) {
            return 1;
        }
        return -1;
    }
}
