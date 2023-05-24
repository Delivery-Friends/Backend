package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.Store.*;
import DeliveryFriends.Backend.Service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping("/addStore")
    public BaseResponse<Long> addStore(@RequestBody CreateStoreDto createStoreDto) {
        return new BaseResponse<>(storeService.addStore(createStoreDto));
    }

    @PostMapping("/addMenu")
    public BaseResponse<Long> addMenu(@RequestBody CreateMenuDto createMenuDto) {
        return new BaseResponse<>(storeService.addMenu(createMenuDto));
    }

    @PostMapping("/addMenuOptionGroup")
    public BaseResponse<Long> addMenuOptionGroup(@RequestBody CreateMenuOptionGroupDto req) {
        return new BaseResponse<>(storeService.addMenuOptionGroup(req));
    }

    @PostMapping("/addMenuOption")
    public BaseResponse<Long> addMenuOption(@RequestBody CreateMenuOptionDto req) {
        return new BaseResponse<>(storeService.addMenuOption(req));
    }

    @GetMapping("/stores")
    public BaseResponse<List<ReadStoresDto>> getStoreList(
            @PageableDefault(size = 10, sort = "reviewCount", direction = Sort.Direction.ASC) Pageable pageable,
            StoreCondDto cond
            ) {
        return new BaseResponse<>(storeService.getStoreList(pageable, cond));
    }

    @GetMapping("/store/menu/{storeId}")
    public BaseResponse<List<ReadMenuDto>> getStoreMenu(@PathVariable Long storeId) {
        return new BaseResponse<>(storeService.readMenu(storeId));
    }
}
