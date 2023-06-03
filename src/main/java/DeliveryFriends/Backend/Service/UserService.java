package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Config.Security.TokenProvider;
import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Domain.*;
import DeliveryFriends.Backend.Domain.Dto.FilenameDto;
import DeliveryFriends.Backend.Domain.Dto.Store.ReadStoresDto;
import DeliveryFriends.Backend.Domain.Dto.Store.SimpleStoreDto;
import DeliveryFriends.Backend.Domain.Dto.Store.StoreCondDto;
import DeliveryFriends.Backend.Domain.Dto.User.*;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;
import static DeliveryFriends.Backend.Domain.QUserOrder.userOrder;

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
    private final ChoiceOptionRepository choiceOptionRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final CartRepository cartRepository;
    private final LikeStoreRepository likeStoreRepository;
    private final StoreMediaRepository storeMediaRepository;
    private final UserOrderRepository userOrderRepository;

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

            List<CartMenuRes> cartMenus = new ArrayList<>();
            //가게의 메뉴
            for (ChoiceMenu choiceMenu : choiceMenus) {
                CartMenuRes cartMenuRes = new CartMenuRes();

                cartMenuRes.setName(choiceMenu.getMenu().getName());
                cartMenuRes.setPrice(choiceMenu.getMenu().getPrice());
                cartMenuRes.setCount(choiceMenu.getCount());

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
            LikeStore likeStore = new LikeStore(store, user);
            likeStoreRepository.delete(likeStore);
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
                        (float) (store.getReviewScore() / store.getReviewCount()),
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

    public List<UserOrdersDto> getUserOrderList(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        List<UserOrder> userOrders = userOrderRepository.findByUser(findUser.get());
        List<UserOrdersDto> result = new ArrayList<>();
        for (UserOrder userOrder : userOrders) {
            String orderResult = userOrder.getResult();
            if (orderResult.equals("결제 완료")) {
                if (userOrder.getTeam().getGroupEndTime().isBefore(LocalDateTime.now())) {
                    orderResult = "팀 주문 취소";
                }
            }
            UserOrdersDto userOrdersDto = new UserOrdersDto(orderResult, userOrder.getStore().getName(), userOrder.getOrderInfo());
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

        String orderResult = userOrder.getResult();
        if (orderResult.equals("결제 완료")) {
            if (userOrder.getTeam().getGroupEndTime().isBefore(LocalDateTime.now())) {
                orderResult = "팀 주문 취소";
            }
        }
        return new UserOrderDto(orderResult, userOrder.getPaymentKey(), userOrder.getStore().getName(), userOrder.getOrderInfo());
    }
}
