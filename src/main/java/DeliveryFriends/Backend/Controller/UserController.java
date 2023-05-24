package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Domain.Dto.User.CreateUserReq;
import DeliveryFriends.Backend.Domain.Dto.User.OnlyCodeDto;
import DeliveryFriends.Backend.Service.JWTService;
import DeliveryFriends.Backend.Service.OauthService;
import DeliveryFriends.Backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OauthService oauthService;
    private final JWTService jwtService;
    /**
     *  https://kauth.kakao.com/oauth/authorize?client_id=7173ff640512dedb87315dd3ad7a74db&redirect_uri=http://localhost:9000/oauth/kakao/login&response_type=code
     *  https://kauth.kakao.com/oauth/authorize?client_id=7173ff640512dedb87315dd3ad7a74db&redirect_uri=https://prod.jaehwan.shop/oauth/kakao/login&response_type=code
     */
    // 카카오 id 호출
    @GetMapping("/oauth/kakao/login")
    public BaseResponse<TokensDto> loginByKakao(String code) throws BaseException {
        if (code == null) {
            throw new BaseException(KAKAO_SERVER_ERROR);
        }
        return new BaseResponse<> (oauthService.getKakaoTokens(code));
    }

    @GetMapping("/test")
    public BaseResponse<String> test() throws BaseException {

        return new BaseResponse<> ("성공");
    }

    @GetMapping("/loginTest")
    public BaseResponse<String> loginTest() throws BaseException {
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
}
