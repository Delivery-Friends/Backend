package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Domain.*;
import DeliveryFriends.Backend.Domain.Dto.FilenameDto;
import DeliveryFriends.Backend.Domain.Dto.PopularCategoryDto;
import DeliveryFriends.Backend.Domain.Dto.Store.*;
import DeliveryFriends.Backend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final StoreMediaRepository storeMediaRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final PopularCategoryRepository popularCategoryRepository;
    private final UserRepository userRepository;
    private final UserOrderRepository userOrderRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMediaRepository reviewMediaRepository;
    private final MenuMediaRepository menuMediaRepository;

    public Long addStore(CreateStoreDto createStoreDto) {
        Store store = new Store(createStoreDto, 0L, 0L, 0L, 0L);
        return storeRepository.save(store).getId();
    }

    public StoreInfoDto getStoreInfo(Long storeId) {
        Optional<Store> findStore = storeRepository.findById(storeId);
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }
        Store store = findStore.get();
        return new StoreInfoDto(store);
    }

    public List<ReadStoresDto> getStoreList(Pageable pageable, StoreCondDto cond) {
        List<SimpleStoreDto> storeList = storeRepository.getStoreList(pageable, cond);
        if (StringUtils.hasText(cond.getCategory())) {
            PopularCategory popularCategory = new PopularCategory(cond.getCategory());
            popularCategoryRepository.save(popularCategory);
        }

        List<ReadStoresDto> result = new ArrayList<>();
        for (SimpleStoreDto simpleStoreDto : storeList) {
            List<FilenameDto> medium = storeMediaRepository.getStoreMedium(simpleStoreDto.getId());
            if (simpleStoreDto.getReviewCount() > 0) {
                ReadStoresDto readStoresDto = new ReadStoresDto(
                        simpleStoreDto.getId(),
                        simpleStoreDto.getName(),
                        simpleStoreDto.getDeliveryWaitTime(),
                        simpleStoreDto.getPackageAvailable(),
                        simpleStoreDto.getPackageWaitTime(),
                        simpleStoreDto.getDeliveryTip(),
                        (float) (simpleStoreDto.getReviewScore() / simpleStoreDto.getReviewCount()),
                        simpleStoreDto.getReviewCount(),
                        simpleStoreDto.getMinPrice(),
                        medium
                );
                result.add(readStoresDto);
            } else {
                ReadStoresDto readStoresDto = new ReadStoresDto(
                        simpleStoreDto.getId(),
                        simpleStoreDto.getName(),
                        simpleStoreDto.getDeliveryWaitTime(),
                        simpleStoreDto.getPackageAvailable(),
                        simpleStoreDto.getPackageWaitTime(),
                        simpleStoreDto.getDeliveryTip(),
                        0F,
                        simpleStoreDto.getReviewCount(),
                        simpleStoreDto.getMinPrice(),
                        medium
                );
                result.add(readStoresDto);
            }
        }
        return result;
    }

    public List<String> getPopularCategory() {
        List<String> categories = new ArrayList<>();
        categories.add("한식");
        categories.add("중식");
        categories.add("일식");
        categories.add("양식");
        categories.add("분식");
        categories.add("치킨");
        categories.add("피자");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = LocalDateTime.now().minusHours(1);
        ArrayList<PopularCategoryDto> arr = new ArrayList<>();
        for (String category : categories) {
            Long count = popularCategoryRepository.countByCategoryAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(category, past, now);
            arr.add(new PopularCategoryDto(category, count));
        }

        Collections.sort(arr);

        List<String> result = new ArrayList<>();
        result.add(arr.get(0).getCategory());
        result.add(arr.get(1).getCategory());
        result.add(arr.get(2).getCategory());
        result.add(arr.get(3).getCategory());
        result.add(arr.get(4).getCategory());

        return result;
    }

    public Long addMenu(CreateMenuDto createMenuDto) {
        Optional<Store> findStore = storeRepository.findById(createMenuDto.getStoreId());
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }
        Menu menu = new Menu(createMenuDto, findStore.get());
        return menuRepository.save(menu).getId();
    }

    public Long addMenuOptionGroup(CreateMenuOptionGroupDto createMenuOptionGroupDto) {
        Optional<Menu> findMenu = menuRepository.findById(createMenuOptionGroupDto.getMenuId());
        if (!findMenu.isPresent()) {
            throw new BaseException(CANNOT_FOUND_MENU);
        }
        MenuOptionGroup menuOptionGroup = new MenuOptionGroup(createMenuOptionGroupDto, findMenu.get());
        return menuOptionGroupRepository.save(menuOptionGroup).getId();
    }

    public Long addMenuOption(CreateMenuOptionDto createMenuOptionDto) {
        Optional<MenuOptionGroup> findMenuOptionGroup = menuOptionGroupRepository.findById(createMenuOptionDto.getMenuOptionGroupId());
        if (!findMenuOptionGroup.isPresent()) {
            throw new BaseException(CANNOT_FOUND_MENU_OPTION_GROUP);
        }
        MenuOption menuOptionGroup = new MenuOption(createMenuOptionDto, findMenuOptionGroup.get());
        return menuOptionRepository.save(menuOptionGroup).getId();
    }

    public List<ReadMenuDto> readMenu(Long storeId) {
        List<ReadMenuDto> result = new ArrayList<>();

        Optional<Store> findStore = storeRepository.findById(storeId);
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }
        List<Menu> menus = menuRepository.findByStoreAndDeleted(findStore.get(), false);
        for (Menu menu : menus) {
            List<MenuMedia> menuMedium = menuMediaRepository.findByMenu(menu);
            List<String> medium = new ArrayList<>();
            for (MenuMedia menuMedia : menuMedium) {
                medium.add(menuMedia.getFileName());
            }
            ReadMenuDto readMenuDto = new ReadMenuDto();
            readMenuDto.setId(menu.getId());
            readMenuDto.setName(menu.getName());
            readMenuDto.setExpression(menu.getExpression());
            readMenuDto.setPrice(menu.getPrice());
            readMenuDto.setMedium(medium);

            List<MenuOptionGroup> menuOptionGroups = menuOptionGroupRepository.findByMenuAndDeleted(menu, false);
            List<ReadMenuOptionGroup> readMenuOptionGroups = new ArrayList<>();
            for (MenuOptionGroup menuOptionGroup : menuOptionGroups) {
                ReadMenuOptionGroup readMenuOptionGroup = new ReadMenuOptionGroup();
                readMenuOptionGroup.setId(menuOptionGroup.getId());
                readMenuOptionGroup.setMultiSelect(menuOptionGroup.getMultiSelect());
                readMenuOptionGroup.setName(menuOptionGroup.getName());

                List<ReadMenuOption> readMenuOptions = new ArrayList<>();
                List<MenuOption> menuOptions = menuOptionRepository.findByMenuOptionGroupAndDeleted(menuOptionGroup, false);
                for (MenuOption menuOption : menuOptions) {
                    ReadMenuOption readMenuOption = new ReadMenuOption();
                    readMenuOption.setId(menuOption.getId());
                    readMenuOption.setName(menuOption.getName());
                    readMenuOption.setPrice(menuOption.getPrice());
                    readMenuOption.setDefaultValue(menuOption.getDefaultValue());
                    readMenuOption.setMaxCount(menuOption.getMaxCount());
                    readMenuOptions.add(readMenuOption);
                }
                readMenuOptionGroup.setReadMenuOptionList(readMenuOptions);
                readMenuOptionGroups.add(readMenuOptionGroup);
            }
            readMenuDto.setReadMenuOptionGroupList(readMenuOptionGroups);
            result.add(readMenuDto);
        }
        return result;
    }

    public List<ReviewRes> addReview(ReviewReq req) throws BaseException {
        Optional<User> findUser = userRepository.findById(req.getUserId());
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        Optional<UserOrder> findOrder = userOrderRepository.findById(req.getOrderId());
        if (!findOrder.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }
        if (reviewRepository.findByOrder(findOrder.get()).isPresent()) {
            throw new BaseException(ALREADY_WRITED_REVIEW);
        }
        UserOrder userOrder = findOrder.get();
        Store store = userOrder.getStore();
        store.setReviewScore(store.getReviewScore() + req.getScore());
        store.setReviewCount(store.getReviewCount() + 1);

        Review review = new Review(req.getScore(), req.getContent(), userOrder, user, store);

        for (String filename : req.getMedia()) {
            ReviewMedia reviewMedia = new ReviewMedia(filename, review);
            reviewMediaRepository.save(reviewMedia);
        }
        reviewRepository.save(review);

        return getReview(store.getId());
    }

    public List<ReviewRes> getReview(Long storeId) {
        Optional<Store> findStore = storeRepository.findById(storeId);
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }
        List<ReviewRes> result = new ArrayList<>();
        List<Review> reviews = reviewRepository.findByStore(findStore.get());
        for (Review review : reviews) {
            List<ReviewMedia> reviewMedium = reviewMediaRepository.findByReview(review);
            List<String> medium = new ArrayList<>();
            for (ReviewMedia reviewMedia : reviewMedium) {
                medium.add(reviewMedia.getFileName());
            }

            ReviewRes reviewRes = new ReviewRes(
                    review.getUser().getNickname(),
                    review.getUser().getImgSrc(),
                    review.getScore(),
                    review.getContent(),
                    review.getCreatedAt(),
                    medium
            );
            result.add(reviewRes);
        }
        return result;
    }
}
