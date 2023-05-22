package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Domain.Dto.User.CreateUserReq;
import DeliveryFriends.Backend.Service.OauthService;
import DeliveryFriends.Backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final OauthService oauthService;
    private final UserService userService;
    /**
     *  https://kauth.kakao.com/oauth/authorize?client_id=7173ff640512dedb87315dd3ad7a74db&redirect_uri=http://localhost:9000/oauth/kakao/login&response_type=code
     *  https://kauth.kakao.com/oauth/authorize?client_id=7173ff640512dedb87315dd3ad7a74db&redirect_uri=https://jaehwan.shop/oauth/kakao/login&response_type=code
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

    @GetMapping("/oauth/kakao/join")
    public BaseResponse<String> joinByKakao(String code) throws BaseException {
        if (code == null) {
            throw new BaseException(KAKAO_SERVER_ERROR);
        }
        return new BaseResponse<> (oauthService.getKakaoId(code));
    }

    @PostMapping("/join")
    public BaseResponse<TokensDto> join(CreateUserReq createUserReq) throws BaseException {
        return new BaseResponse<TokensDto>(userService.createUser(createUserReq));
    }
}
