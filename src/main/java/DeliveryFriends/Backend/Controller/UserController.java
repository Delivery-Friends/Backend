package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Config.Security.TokenProvider;
import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoCode;
import DeliveryFriends.Backend.Domain.Dto.Store.ReadStoresDto;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Domain.Dto.User.*;
import DeliveryFriends.Backend.Service.JWTService;
import DeliveryFriends.Backend.Service.OauthService;
import DeliveryFriends.Backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.KAKAO_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OauthService oauthService;
    private final JWTService jwtService;
    private final TokenProvider tokenProvider;

    /**
     *  https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=7173ff640512dedb87315dd3ad7a74db&redirect_uri=http://localhost:9000/api/getKakaoAccessToken
     */
    /**
     * http://localhost:9000/oauth/kakao/login?code=lQk1bAOz-RoaOLf4DigBHK3UJD3WJ3gB7beJKwA06PBn5LWsOnigzj_Z6NC302CzSaDq9go9dVwAAAGIWIx29g
     */
    // 카카오 id 호출
    @GetMapping("/getKakaoAccessToken")
    public BaseResponse<String> getKakaoAccessToken(KakaoCode req) throws BaseException {
        if (req == null) {
            throw new BaseException(KAKAO_SERVER_ERROR);
        }
        return new BaseResponse<> (oauthService.getKakaoAccessToken(req.getCode()));
    }

    /**
     *  https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=7173ff640512dedb87315dd3ad7a74db&redirect_uri=http://localhost:9000/api/onlyTestingLogin
     */
    // 카카오 id 호출
    @GetMapping("/onlyTestingLogin")
    public BaseResponse<TokensDto> loginByKakao(KakaoCode req) throws BaseException {
        if (req == null) {
            throw new BaseException(KAKAO_SERVER_ERROR);
        }
        return new BaseResponse<> (oauthService.getKakaoTokens(req.getCode()));
    }

    /**
     *  http://localhost:9000/oauth/kakao/login?accessToken=VxMGPi1pQCyAeiDQEXL7GCurlNYwL8BaM3CrOYxQCj10mQAAAYhYzSz9
     */
    @GetMapping("oauth/kakao/login")
    public BaseResponse<TokensDto> getAcc(String accessToken) throws BaseException {
        return new BaseResponse<>(oauthService.getTokensByKakaoToken(accessToken));
    }

    @GetMapping("/test")
    public BaseResponse<String> test() throws BaseException {
        return new BaseResponse<> ("성공");
    }

    @GetMapping("/user/loginTest")
    public BaseResponse<String> loginTest(Principal principal) throws BaseException {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<> (userId.toString());
    }

    // 회원가입
    @PostMapping("/join")
    public BaseResponse<CreateUserRes> join(@RequestBody CreateUserReq createUserReq) throws BaseException {
        return new BaseResponse<>(oauthService.createUser(createUserReq));
    }

    @PostMapping("/refresh")
    public BaseResponse<TokensDto> doRefresh() throws BaseException {
        return new BaseResponse<>(tokenProvider.doRefresh());
    }

    @PostMapping("/user/cart/add")
    public void goCart(Principal principal, @RequestBody CartReq req) throws BaseException {
        Long userId = Long.parseLong(principal.getName());
        userService.goCart(req, userId);
    }

    @GetMapping("/user/cart")
    public List<CartRes> getCart(Principal principal) throws BaseException {
        Long userId = Long.parseLong(principal.getName());
        return userService.getCart(userId);
    }

    @PostMapping("/user/cart/delete")
    public List<CartRes> getCart(Principal principal, Long storeId) throws BaseException {
        Long userId = Long.parseLong(principal.getName());
        return userService.deleteCart(userId, storeId);
    }

    @PostMapping("/user/store/like/{storeId}")
    public void storeLike(Principal principal, @PathVariable Long storeId) {
        Long userId = Long.parseLong(principal.getName());
        userService.likeStore(storeId, userId);
    }

    @PostMapping("/user/store/dislike/{storeId}")
    public void storeDislike(Principal principal, @PathVariable Long storeId) {
        Long userId = Long.parseLong(principal.getName());
        userService.dislikeStore(storeId, userId);
    }

    @GetMapping("/user/store/list")
    public List<ReadStoresDto> storeDislike(
            Principal principal,
            @PageableDefault(size = 10, sort = "reviewCount", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Long userId = Long.parseLong(principal.getName());
        return userService.getLikeStoreList(pageable, userId);
    }

    @GetMapping("/user/order")
    public List<UserOrdersDto> getMyOrderList(
            Principal principal,
            @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = Long.parseLong(principal.getName());
        return userService.getUserOrderList(userId);
    }

    @GetMapping("/user/order/{orderId}")
    public UserOrderDto getMyOrderList(@PathVariable Long orderId) {
        return userService.getUserOrder(orderId);
    }
}
