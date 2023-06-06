package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Config.Security.TokenProvider;
import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoCode;
import DeliveryFriends.Backend.Domain.Dto.Store.ReadStoresDto;
import DeliveryFriends.Backend.Domain.Dto.Store.ReviewReq;
import DeliveryFriends.Backend.Domain.Dto.Store.ReviewRes;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Domain.Dto.User.*;
import DeliveryFriends.Backend.Domain.Dto.UserIdTargetIdDto;
import DeliveryFriends.Backend.Service.JWTService;
import DeliveryFriends.Backend.Service.OauthService;
import DeliveryFriends.Backend.Service.StoreService;
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
    private final StoreService storeService;

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
    public BaseResponse<String> goCart(Principal principal, @RequestBody CartReq req) throws BaseException {
        Long userId = Long.parseLong(principal.getName());
        userService.goCart(req, userId);
        return new BaseResponse<>("성공");
    }

    @GetMapping("/user/cart")
    public BaseResponse<List<CartRes>> getCart(Principal principal) throws BaseException {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(userService.getCart(userId));
    }

    @PostMapping("/user/cart/delete")
    public BaseResponse<List<CartRes>> getCart(Principal principal, Long storeId) throws BaseException {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(userService.deleteCart(userId, storeId));
    }

    @PostMapping("/user/store/like/{storeId}")
    public BaseResponse<String> storeLike(Principal principal, @PathVariable Long storeId) {
        Long userId = Long.parseLong(principal.getName());
        userService.likeStore(storeId, userId);
        return new BaseResponse<>("성공");
    }

    @PostMapping("/user/store/dislike/{storeId}")
    public BaseResponse<String> storeDislike(Principal principal, @PathVariable Long storeId) {
        Long userId = Long.parseLong(principal.getName());
        userService.dislikeStore(storeId, userId);
        return new BaseResponse<>("성공");
    }

    @GetMapping("/user/store/list")
    public BaseResponse<List<ReadStoresDto>> storeDislike(
            Principal principal,
            @PageableDefault(size = 100, sort = "reviewCount", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(userService.getLikeStoreList(pageable, userId));
    }

    @GetMapping("/user/order")
    public BaseResponse<List<UserOrdersDto>> getMyOrderList(
            Principal principal,
            @PageableDefault(size = 100, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(userService.getUserOrderList(pageable, userId));
    }

    @GetMapping("/user/order/{orderId}")
    public BaseResponse<UserOrderDto> getMyOrderList(@PathVariable Long orderId) {
        return new BaseResponse<>(userService.getUserOrder(orderId));
    }

    @PostMapping("/user/like/{userId}")
    public BaseResponse<String> likeUser(Principal principal, @PathVariable Long userId) {
        UserIdTargetIdDto req = new UserIdTargetIdDto(Long.parseLong(principal.getName()), userId);
        userService.likeUser(req);
        return new BaseResponse<>("성공");
    }

    @PostMapping("/user/dislike/{userId}")
    public BaseResponse<String> dislikeUser(Principal principal, @PathVariable Long userId) {
        UserIdTargetIdDto req = new UserIdTargetIdDto(Long.parseLong(principal.getName()), userId);
        userService.dislikeUser(req);
        return new BaseResponse<>("성공");
    }

    @GetMapping("/user/likelist")
    public BaseResponse<List<LikeUserRes>> getLikeUserList(
            Principal principal,
            @PageableDefault(size = 100, sort = "reviewCount", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(userService.getLikeUserList(userId, pageable));
    }

    @PostMapping("/user/review")
    public BaseResponse<String> addUserReview(Principal principal, @RequestBody UserReviewReq userReviewReq) {
        Long userId = Long.parseLong(principal.getName());
        userService.addUserReview(userId, userReviewReq);
        return new BaseResponse<>("성공");
    }

    @PostMapping("/user/review/store/add")
    public BaseResponse<List<ReviewRes>> addReview(Principal principal, @RequestBody ReviewReq addReviewReq) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(storeService.addReview(userId, addReviewReq));
    }

    @GetMapping("/user/review/{userId}")
    public BaseResponse<List<UserReviewRes>> getUserReview(@PathVariable Long userId) {
        return new BaseResponse<>(userService.getUserReview(userId));
    }

    @GetMapping("/userinfo/{userId}")
    public BaseResponse<UserInfoDto> getUserInfo(Principal principal, @PathVariable Long userId) {
        try {
            Long myId = Long.parseLong(principal.getName());
            return new BaseResponse<>(userService.getUserInfo(myId, userId));
        }catch (Exception e) {
            return new BaseResponse<>(userService.getUserInfo(null, userId));
        }
    }

    @GetMapping("/user/my")
    public BaseResponse<UserInfoDto> getUserInfo(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(userService.getUserInfo(null, userId));
    }

    @PostMapping("/user/profile")
    public BaseResponse<String> updateProfile(Principal principal, @RequestBody ContentDto contentDto) {
        Long userId = Long.parseLong(principal.getName());
        userService.updateProfile(userId, contentDto.getContent());
        return new BaseResponse<>("성공");
    }

    @PostMapping("/user/nickname")
    public BaseResponse<String> updateNickname(Principal principal, @RequestBody ContentDto contentDto) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(userService.updateNickname(userId, contentDto.getContent()));
    }

    @GetMapping("/nickname")
    public BaseResponse<String> updateNickname(String nickname) {
        return new BaseResponse<>(userService.isValidNickname(nickname));
    }

    @GetMapping("/user/myReview")
    public BaseResponse<List<UserReviewRes>> getMyReview(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(userService.getUserReview(userId));
    }
}
