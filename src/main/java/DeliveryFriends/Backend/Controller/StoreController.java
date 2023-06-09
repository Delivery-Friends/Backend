package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.Store.*;
import DeliveryFriends.Backend.Service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping("/addAll")
    public BaseResponse<String> add(@RequestBody CreateAllDto createAllDto) {
        return new BaseResponse<>(storeService.addAll(createAllDto));
    }

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

    @GetMapping("/store/{storeId}")
    public BaseResponse<StoreInfoDto> getStoreInfo(Principal principal, @PathVariable Long storeId) {
        try {
            Long myId = Long.parseLong(principal.getName());
            return new BaseResponse<>(storeService.getStoreInfo(myId, storeId));
        }catch (Exception e) {
            return new BaseResponse<>(storeService.getStoreInfo(null, storeId));
        }
    }

    @GetMapping("/stores")
    public BaseResponse<List<ReadStoresDto>> getStoreList(
            @PageableDefault(size = 100, sort = "reviewCount", direction = Sort.Direction.DESC) Pageable pageable,
            StoreCondDto cond
            ) {
        return new BaseResponse<>(storeService.getStoreList(pageable, cond));
    }

    @GetMapping("/store/menu/{storeId}")
    public BaseResponse<List<ReadMenuDto>> getStoreMenu(@PathVariable Long storeId) {
        return new BaseResponse<>(storeService.readMenu(storeId));
    }

    @GetMapping("/popular/category")
    public BaseResponse<List<String>> getPopularMenu() {
        return new BaseResponse<>(storeService.getPopularCategory());
    }

    @GetMapping("/store/review/{storeId}")
    public BaseResponse<List<ReviewRes>> getReview(@PathVariable Long storeId) {
        return new BaseResponse<>(storeService.getReview(storeId));
    }

}
