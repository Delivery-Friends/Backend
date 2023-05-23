package DeliveryFriends.Backend.Domain.Dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FilenameDto {
    String filename;

    @QueryProjection
    public FilenameDto(String filename) {
        this.filename = filename;
    }
}
