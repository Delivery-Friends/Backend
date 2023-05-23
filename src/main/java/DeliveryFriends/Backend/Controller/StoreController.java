package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.Store.*;
import DeliveryFriends.Backend.Service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping("/addStore")
    public Long addStore(@RequestBody CreateStoreDto createStoreDto) {
        return storeService.addStore(createStoreDto);
    }

    @PostMapping("/addMenu")
    public Long addMenu(@RequestBody CreateMenuDto createMenuDto) {
        return storeService.addMenu(createMenuDto);
    }

    @GetMapping("/stores")
    public BaseResponse<List<ReadStoresDto>> getStoreList(
            @PageableDefault(size = 10, sort = "reviewCount", direction = Sort.Direction.ASC) Pageable pageable,
            StoreCondDto cond
            ) {
        return new BaseResponse<List<ReadStoresDto>>(storeService.getStoreList(pageable, cond));
    }
}
