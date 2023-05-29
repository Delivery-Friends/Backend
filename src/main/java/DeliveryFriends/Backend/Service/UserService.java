package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Domain.*;
import DeliveryFriends.Backend.Domain.Dto.User.*;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;
    private final ChoiceOptionRepository choiceOptionRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final CartRepository cartRepository;

    public Long getInfo() {
        Long userId = jwtService.getInfo();

        return userId;
    }

    public TokensDto createUser(CreateUserReq req) throws BaseException {
        if (StringUtils.hasText(req.getKakaoId()) || StringUtils.hasText(req.getNickname()) || StringUtils.hasText(req.getName())) {
            throw new BaseException(Bad_Request);
        }
        try {
            Optional<User> findUser = userRepository.findByKakaoId(req.getKakaoId());
            if(findUser.isPresent()) {
                throw new BaseException(EXISTS_KAKAOID);
            }

            User user = userRepository.save(
                    User.builder()
                            .name(req.getName())
                            .nickname(req.getNickname())
                            .kakaoId(req.getKakaoId())
                            .point(0L)
                            .build());

            return jwtService.createJwt(user.getId());

        } catch (BaseException e) {
            throw e;
        }
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

            List<CartMenuRes> cartMenus = new ArrayList<>();
            //가게의 메뉴
            for (ChoiceMenu choiceMenu : choiceMenus) {
                CartMenuRes cartMenuRes = new CartMenuRes();

                System.out.println(choiceMenu.getMenu().getName() + "@@@@@시작");


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
                    cartMenus.add(cartMenuRes);
                }

                System.out.println("@!@!: " + cartMenuRes);
                cartRes.setMenus(cartMenus);

                System.out.println(choiceMenu.getMenu().getName() + "@@@@@끝");
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
}
