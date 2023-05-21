package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoCode;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final OauthService oauthService;
    /**
     *  https://kauth.kakao.com/oauth/authorize?client_id=7173ff640512dedb87315dd3ad7a74db&redirect_uri=http://localhost:9000/oauth/kakao/login&response_type=code
     */
    // 카카오 id 호출
    @GetMapping("/oauth/kakao/login")
    public BaseResponse<TokensDto> authenticateKakao(String code) throws BaseException {
        if (code == null) {
            throw new BaseException(Unauthorized);
        }
        return new BaseResponse<> (oauthService.getKakaoToken(code));
    }
}
