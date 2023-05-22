package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Controller.feign.KakaoKapiFeign;
import DeliveryFriends.Backend.Controller.feign.KakaoKauthFeign;
import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoInfoRes;
import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoToken;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Domain.User;
import DeliveryFriends.Backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.Unauthorized;

@Service
@RequiredArgsConstructor
public class OauthService {

    private String kakaoURL = "https://kauth.kakao.com/oauth/token";

    private final UserRepository userRepository;
    private final KakaoKauthFeign kakaoKauthFeign;
    private final KakaoKapiFeign kakaoKapiFeign;
    private final JWTService jwtService;

    @Value("${KAKAO_API_KEY}")
    private String KAKAO_API_KEY;

    private final BCryptPasswordEncoder passwordEncoder;

    public TokensDto getKakaoTokens(String code) {
        // 카카오에서 토큰 받기
        KakaoToken kakaoToken = kakaoKauthFeign.getAccessToken(
                "authorization_code", KAKAO_API_KEY
                , "http://localhost:9000/oauth/kakao/login", code
        );

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + kakaoToken.getAccess_token());

        KakaoInfoRes kakaoInfoRes = kakaoKapiFeign.getUser(headerMap);

        Optional<User> findMember = userRepository.findByKakaoId(kakaoInfoRes.getId());

        if (findMember.isPresent()) {
            return jwtService.createJwt(findMember.get().getId());
        } else {
            throw new BaseException(Unauthorized);
        }
    }

    public String getKakaoId(String code) {
        // 카카오에서 토큰 받기
        KakaoToken kakaoToken = kakaoKauthFeign.getAccessToken(
                "authorization_code", KAKAO_API_KEY
                , "http://localhost:9000/oauth/kakao/login", code
        );

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + kakaoToken.getAccess_token());

        KakaoInfoRes kakaoInfoRes = kakaoKapiFeign.getUser(headerMap);

        return kakaoInfoRes.getId();
    }
}
