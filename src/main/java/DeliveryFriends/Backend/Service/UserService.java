package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Domain.*;
import DeliveryFriends.Backend.Domain.Dto.FilenameDto;
import DeliveryFriends.Backend.Domain.Dto.Store.ReadStoresDto;
import DeliveryFriends.Backend.Domain.Dto.User.*;
import DeliveryFriends.Backend.Domain.Dto.UserIdTargetIdDto;
import DeliveryFriends.Backend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;
    private final MenuMediaRepository menuMediaRepository;
    private final ChoiceOptionRepository choiceOptionRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final CartRepository cartRepository;
    private final LikeStoreRepository likeStoreRepository;
    private final StoreMediaRepository storeMediaRepository;
    private final UserOrderRepository userOrderRepository;
    private final LikeUserRepository likeUserRepository;
    private final UserReviewRepository userReviewRepository;
    private final TeamOrderRepository teamOrderRepository;
    private final ReviewRepository reviewRepository;

    public void updateProfile(Long userId, String imgSrc) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        user.setImgSrc(imgSrc);
    }

    public String updateNickname(Long userId, String nickname) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        if (isValidNickname(nickname).equals("가능한 닉네임입니다.")) {
            User user = findUser.get();
            user.setNickname(nickname);
        }
        return "성공";
    }

    public String isValidNickname(String nickname) {
        // 닉네임 입력 여부
        if (nickname.length() == 0) {
            throw new BaseException(NICKNAME_ENTER);
        }
        // 닉네임 길이 < 3
        if ((nickname.length() < 3)) {
            throw new BaseException(NICKNAME_LENGTH_MIN_INSUFFICIENT);
        }

        // 닉네임 길이 > 10
        if (nickname.length() > 10) {
            throw new BaseException(NICKNAME_LENGTH_MAX_INSUFFICIENT);
        }

        // 닉네임 한글영어 2~10
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{2,10}$");
        if (!pattern.matcher(nickname).find()) {
            throw new BaseException(NICKNAME_NOT_ALLOW_CHARACTER);
        }

        // 닉네임 중복
        if (isDuplicatedNickname(nickname)) {
            throw new BaseException(NICKNAME_ALREADY_USE);
        }
        return "가능한 닉네임입니다.";
    }
    public Boolean isDuplicatedNickname(String nickname) {
        return userRepository.findByNicknameIgnoreCase(nickname).isPresent();
    }

    public void goCart(CartReq req, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        Optional<Store> findStore = storeRepository.findById(req.getStoreId());
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }
        Store store = findStore.get();
        Optional<Cart> findCart = cartRepository.findByUserAndStoreAndDeleted(user, store, false);
        Cart cart = null;
        if (!findCart.isPresent()) {
            cart = new Cart(user, store);
            cartRepository.save(cart);
        } else {
            cart = findCart.get();
        }

        List<ChoiceMenuReq> menus = req.getMenu();
        for (ChoiceMenuReq choiceMenuReq : menus) {
            Long menuId = choiceMenuReq.getMenuId();
            Optional<Menu> findMenu = menuRepository.findById(menuId);
            if (!findMenu.isPresent()) {
                throw new BaseException(CANNOT_FOUND_MENU);
            }
            ChoiceMenu choiceMenu = new ChoiceMenu(choiceMenuReq.getCount(), cart, user, findMenu.get());
            choiceMenuRepository.save(choiceMenu);

            List<ChoiceOptionReq> choiceOptions = choiceMenuReq.getChoiceOption();
            for (ChoiceOptionReq choiceOption : choiceOptions) {
                Long count = choiceOption.getCount();
                Optional<MenuOption> findMenuOption = menuOptionRepository.findById(choiceOption.getMenuOptionId());
                if (!findMenuOption.isPresent()) {
                    throw new BaseException(CANNOT_FOUND_MENU_OPTION);
                }

                ChoiceOption choiceOptionE = new ChoiceOption(count, user, choiceMenu, findMenuOption.get());

                choiceOptionRepository.save(choiceOptionE);
            }
        }
    }

    public List<CartRes> getCart(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        List<Cart> carts = cartRepository.findByUserAndDeleted(user, false);
        List<CartRes> result = new ArrayList<>();
        // 모든 카트
        for (Cart cart : carts) {
            List<ChoiceMenu> choiceMenus = choiceMenuRepository.findByCart(cart);

            CartRes cartRes = new CartRes();
            cartRes.setCartId(cart.getId());
            cartRes.setStoreId(cart.getStore().getId());
            cartRes.setStoreName(cart.getStore().getName());
            cartRes.setDeliveryTip(cart.getStore().getDeliveryTip());

            List<String> storeFilenames = new ArrayList<>();
            List<FilenameDto> medium = storeMediaRepository.getStoreMedium(cart.getStore().getId());
            for (FilenameDto filenameDto : medium) {
                storeFilenames.add(filenameDto.getFilename());
            }
            cartRes.setMedium(storeFilenames);

            List<CartMenuRes> cartMenus = new ArrayList<>();
            //가게의 메뉴
            for (ChoiceMenu choiceMenu : choiceMenus) {
                CartMenuRes cartMenuRes = new CartMenuRes();

                cartMenuRes.setName(choiceMenu.getMenu().getName());
                cartMenuRes.setPrice(choiceMenu.getMenu().getPrice());
                cartMenuRes.setCount(choiceMenu.getCount());

                List<String> menuFilenames = new ArrayList<>();
                List<MenuMedia> menus = menuMediaRepository.findByMenu(choiceMenu.getMenu());
                for (MenuMedia menuMedia : menus) {
                    menuFilenames.add(menuMedia.getFileName());
                }
                cartMenuRes.setMedium(menuFilenames);

                //가게의 옵션
                List<ChoiceOption> choiceOptions = choiceOptionRepository.findByChoiceMenu(choiceMenu);
                List<CartOptionRes> cartOptionsRes = new ArrayList<>();
                for (ChoiceOption choiceOption : choiceOptions) {
                    CartOptionRes cartOptionRes = new CartOptionRes();
                    cartOptionRes.setName(choiceOption.getMenuOption().getName());
                    cartOptionRes.setPrice(choiceOption.getMenuOption().getPrice());
                    cartOptionRes.setCount(choiceOption.getCount());
                    cartOptionsRes.add(cartOptionRes);
                    cartMenuRes.setOptions(cartOptionsRes);
                }
                cartMenus.add(cartMenuRes);
                cartRes.setMenus(cartMenus);
            }
            result.add(cartRes);
        }
        return result;
    }

    public List<CartRes> deleteCart(Long userId, Long storeId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();

        Optional<Store> findStore = storeRepository.findById(storeId);
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }
        Optional<Cart> findDeleteCart = cartRepository.findByUserAndStoreAndDeleted(user, findStore.get(), false);
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_CART);
        }
        List<ChoiceMenu> deleteChoiceMenus = choiceMenuRepository.findByCart(findDeleteCart.get());

        //가게의 메뉴
        for (ChoiceMenu choiceMenu : deleteChoiceMenus) {
            //가게의 옵션
            List<ChoiceOption> choiceOptions = choiceOptionRepository.findByChoiceMenu(choiceMenu);
            for (ChoiceOption choiceOption : choiceOptions) {
                choiceOptionRepository.delete(choiceOption);
            }
            choiceMenuRepository.delete(choiceMenu);
        }
        cartRepository.delete(findDeleteCart.get());

        List<CartRes> result = getCart(userId);

        return result;
    }

    public void likeStore(Long storeId, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();

        Optional<Store> findStore = storeRepository.findById(storeId);
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }
        Store store = findStore.get();

        Optional<LikeStore> findLikeStore = likeStoreRepository.findByStoreAndUser(store, user);
        if (findLikeStore.isPresent()) {
            throw new BaseException(ALREADY_LIKE);
        } else {
            LikeStore likeStore = new LikeStore(store, user);
            likeStoreRepository.save(likeStore);
            store.setLikeCount(store.getLikeCount() + 1);
        }
    }

    public void dislikeStore(Long storeId, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();

        Optional<Store> findStore = storeRepository.findById(storeId);
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }
        Store store = findStore.get();

        Optional<LikeStore> findLikeStore = likeStoreRepository.findByStoreAndUser(store, user);
        if (!findLikeStore.isPresent()) {
            throw new BaseException(ALREADY_DISLIKE);
        } else {
            likeStoreRepository.delete(findLikeStore.get());
            store.setLikeCount(store.getLikeCount() - 1);
        }
    }

    public List<ReadStoresDto> getLikeStoreList(Pageable pageable, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();

        List<LikeStore> likeStores = likeStoreRepository.findByUser(user);
        List<Long> storeIds = new ArrayList<>();
        for (LikeStore likeStore : likeStores) {
            storeIds.add(likeStore.getStore().getId());
        }
        List<Store> stores = storeRepository.findByIdIn(pageable, storeIds);

        List<ReadStoresDto> result = new ArrayList<>();
        for (Store store : stores) {
            List<FilenameDto> medium = storeMediaRepository.getStoreMedium(store.getId());
            if (store.getReviewCount() > 0) {
                ReadStoresDto readStoresDto = new ReadStoresDto(
                        store.getId(),
                        store.getName(),
                        store.getDeliveryWaitTime(),
                        store.getPackageAvailable(),
                        store.getPackageWaitTime(),
                        store.getDeliveryTip(),
                        (float) (store.getReviewScore()) / (float) (store.getReviewCount()),
                        store.getReviewCount(),
                        store.getMinPrice(),
                        medium
                );
                result.add(readStoresDto);
            } else {
                ReadStoresDto readStoresDto = new ReadStoresDto(
                        store.getId(),
                        store.getName(),
                        store.getDeliveryWaitTime(),
                        store.getPackageAvailable(),
                        store.getPackageWaitTime(),
                        store.getDeliveryTip(),
                        0F,
                        store.getReviewCount(),
                        store.getMinPrice(),
                        medium
                );
                result.add(readStoresDto);
            }
        }
        return result;
    }

    public List<UserOrdersDto> getUserOrderList(Pageable pageable, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        List<UserOrder> userOrders = userOrderRepository.findByUser(user);

        List<UserOrdersDto> result = new ArrayList<>();
        for (UserOrder userOrder : userOrders) {
            Long storeId = userOrder.getStore().getId();
            String storeName = userOrder.getStore().getName();
            List<String> medium = new ArrayList<>();
            List<StoreMedia> storeMedium = storeMediaRepository.findByStore(userOrder.getStore());
            for (StoreMedia storeMedia : storeMedium) {
                String fileName = storeMedia.getFileName();
                medium.add(fileName);
            }
            Team team = userOrder.getTeam();
            Long leaderId = team.getLeaderId();
            Long orderId = userOrder.getId();
            String orderInfo = userOrder.getOrderInfo();
            LocalDateTime createdDate = userOrder.getCreatedAt();

            String menuInfo = userOrder.getMenuInfo();
            Long price = userOrder.getPrice();
            Long deliveryTip = userOrder.getDeliveryTip();
            Boolean isUserReviewWrite = false;
            Boolean isStoreLikeReviewWrite = false;


            Optional<User> findLeader = userRepository.findById(userId);
            if (!findLeader.isPresent()) {
                throw new BaseException(CANNOT_FOUND_USER);
            }
            User leader = findUser.get();
            Optional<UserReview> findReivew = userReviewRepository.findByUserAndWriter(leader, user);
            isUserReviewWrite = findReivew.isPresent();

            isStoreLikeReviewWrite = reviewRepository.findByOrder(userOrder).isPresent();

            UserOrdersDto userOrdersDto = new UserOrdersDto(storeId,
                    storeName,
                    medium,
                    leaderId,
                    orderId,
                    orderInfo,
                    createdDate,
                    menuInfo,
                    price,
                    deliveryTip,
                    isStoreLikeReviewWrite,
                    isUserReviewWrite
            );
            result.add(userOrdersDto);
        }
        return result;
    }

    public UserOrderDto getUserOrder(Long userOrderId) {
        Optional<UserOrder> findUserOrder = userOrderRepository.findById(userOrderId);
        if (!findUserOrder.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER_ORDER);
        }
        UserOrder userOrder = findUserOrder.get();

        String orderInfo = userOrder.getOrderInfo();
        if (orderInfo.equals("결제 완료")) {
            if (userOrder.getTeam().getGroupEndTime().isBefore(LocalDateTime.now())) {
                orderInfo = "팀 주문 취소";
            }
        }
        return new UserOrderDto(orderInfo, userOrder.getPaymentKey(), userOrder.getStore().getName(), userOrder.getOrderInfo());
    }

    public void likeUser(UserIdTargetIdDto req) {
        Long userId = req.getUserId();
        Long targetId = req.getTargetId();
        System.out.println("@@@" + userId);
        System.out.println("@@@" + targetId);
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        Optional<User> findTarget = userRepository.findById(targetId);
        if (!findTarget.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User target = findTarget.get();

        Optional<LikeUser> findLikeUser = likeUserRepository.findByReceiverAndSender(target, user);
        if (findLikeUser.isPresent()) {
            throw new BaseException(ALREADY_LIKE);
        }

        LikeUser likeUser = new LikeUser(target, user);
        likeUserRepository.save(likeUser);
    }

    public void dislikeUser(UserIdTargetIdDto req) {
        Long userId = req.getUserId();
        Long targetId = req.getTargetId();
        System.out.println("@@@" + userId);
        System.out.println("@@@" + targetId);
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        Optional<User> findTarget = userRepository.findById(targetId);
        if (!findTarget.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User target = findTarget.get();

        Optional<LikeUser> findLikeUser = likeUserRepository.findByReceiverAndSender(target, user);
        if (!findLikeUser.isPresent()) {
            throw new BaseException(ALREADY_DISLIKE);
        }

        likeUserRepository.delete(findLikeUser.get());
    }

    public List<LikeUserRes> getLikeUserList(Long userId, Pageable pageable) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        List<LikeUserRes> result = new ArrayList<>();
        System.out.println(user.getNickname());
        System.out.println("@@@0");
        List<LikeUser> likeUserList = likeUserRepository.findBySender(user);
        System.out.println("@@@1");
        for (LikeUser likeUser : likeUserList) {
            System.out.println("@@@2");
            LikeUserRes likeUserRes = new LikeUserRes();
            User receiver = likeUser.getReceiver();
            likeUserRes.setUserId(receiver.getId());
            likeUserRes.setNickname(receiver.getNickname());
            likeUserRes.setName(receiver.getName());
            likeUserRes.setImgSrc(receiver.getImgSrc());
            System.out.println("@@@3");

            List<UserReview> userReviews = userReviewRepository.findByUser(user);
            Long sum = 0L;
            Long count = 0L;
            for (UserReview userReview : userReviews) {
                sum += userReview.getScore();
                count++;
            }

            if (count < 1) {
                likeUserRes.setScore(0F);
            } else {
                likeUserRes.setScore((float) sum / (float) count);
            }
            System.out.println("@@@4");
            likeUserRes.setReviewCount(Long.valueOf(userReviews.size()));


            System.out.println("@@@5");
            result.add(likeUserRes);
        }
        return result;
    }

    public void addUserReview(Long userId, UserReviewReq userReviewReq) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        Optional<User> findTarget = userRepository.findById(userReviewReq.getUserId());
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User target = findTarget.get();

        Optional<UserReview> findReivew = userReviewRepository.findByUserAndWriter(target, user);
        if (findReivew.isPresent()) {
            throw new BaseException(ALREADY_WRITED_REVIEW);
        }
        UserReview userReview = new UserReview(target, user, userReviewReq.getContent(), userReviewReq.getScore());
        userReviewRepository.save(userReview);
    }

    public List<UserReviewRes> getUserReview(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();

        List<UserReviewRes> result = new ArrayList<>();
        List<UserReview> findReivew = userReviewRepository.findByUser(user);
        for (UserReview userReview : findReivew) {
            UserReviewRes userReviewRes = new UserReviewRes();
            userReviewRes.setUserId(userReview.getWriter().getId());
            userReviewRes.setNickname(userReview.getWriter().getNickname());
            userReviewRes.setImgSrc(userReview.getWriter().getImgSrc());
            userReviewRes.setContent(userReview.getContext());
            userReviewRes.setCreatedDate(userReview.getCreatedAt());
            result.add(userReviewRes);
        }
        return result;
    }

    public UserInfoDto getUserInfo(Long myId, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setNickname(user.getNickname());
        userInfoDto.setImgSrc(user.getImgSrc());

        Long sum = 0L;
        Long count = 0L;
        List<UserReview> userReviews = userReviewRepository.findByUser(user);
        for (UserReview userReview : userReviews) {
            sum += userReview.getScore();
            count++;
        }

        if (count < 1) {
            userInfoDto.setScore(0F);
        } else {
            userInfoDto.setScore((float) sum / (float) count);
        }
        userInfoDto.setReviewCount(Long.valueOf(userReviews.size()));

        Boolean isLike = false;
        if (myId != null) {
            Optional<User> findMe = userRepository.findById(myId);
            if (!findUser.isPresent()) {
                throw new BaseException(CANNOT_FOUND_USER);
            }
            User me = findMe.get();
            Optional<LikeUser> likeUser = likeUserRepository.findByReceiverAndSender(user, me);
            isLike = likeUser.isPresent();
        }
        Long likeCount = likeUserRepository.countByReceiver(user);
        userInfoDto.setLikeCount(likeCount);
        userInfoDto.setIsLike(isLike);

        return userInfoDto;
    }
}
