package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoCode;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Domain.Dto.User.CartRes;
import DeliveryFriends.Backend.Domain.Dto.User.CreateUserReq;
import DeliveryFriends.Backend.Domain.Dto.User.CartReq;
import DeliveryFriends.Backend.Service.JWTService;
import DeliveryFriends.Backend.Service.OauthService;
import DeliveryFriends.Backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.KAKAO_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OauthService oauthService;
    private final JWTService jwtService;

    /**
     *  https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=7173ff640512dedb87315dd3ad7a74db&redirect_uri=http://localhost:9000/onlyTestingLogin
     */
    /**
     * http://localhost:9000/oauth/kakao/login?code=lQk1bAOz-RoaOLf4DigBHK3UJD3WJ3gB7beJKwA06PBn5LWsOnigzj_Z6NC302CzSaDq9go9dVwAAAGIWIx29g
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

        return new BaseResponse<>(oauthService.getAcc(accessToken));
    }

    @GetMapping("/test")
    public BaseResponse<String> test() throws BaseException {
        return new BaseResponse<> ("성공");
    }

    @GetMapping("/loginTest")
    public BaseResponse<String> loginTest() throws BaseException {
        userService.getInfo();
        return new BaseResponse<> ("성공");
    }

    @PostMapping("/join")
    public BaseResponse<TokensDto> join(@RequestBody CreateUserReq createUserReq) throws BaseException {
        return new BaseResponse<>(userService.createUser(createUserReq));
    }

    @PostMapping("/doRefresh")
    public BaseResponse<TokensDto> doRefresh() throws BaseException {
        return new BaseResponse<>(jwtService.doRefresh());
    }

    @PostMapping("/user/cart/add")
    public void goCart(@RequestBody CartReq req) throws BaseException {
        userService.goCart(req, userService.getInfo());
    }

    @GetMapping("/user/cart")
    public List<CartRes> getCart() throws BaseException {
        return userService.getCart(jwtService.getInfo());
    }

    @PostMapping("/user/cart/delete")
    public List<CartRes> getCart(Long storeId) throws BaseException {
        return userService.deleteCart(jwtService.getInfo(), storeId);
    }
}
