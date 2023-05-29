package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Controller.ToJoinException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthService {

    private final UserRepository userRepository;
    private final KakaoKauthFeign kakaoKauthFeign;
    private final KakaoKapiFeign kakaoKapiFeign;
    private final JWTService jwtService;

    @Value("${KAKAO_API_KEY}")
    private String KAKAO_API_KEY;

    private final BCryptPasswordEncoder passwordEncoder;

    public String getKakaoToken(String code) {
        KakaoToken authorization_code = kakaoKauthFeign.getAccessToken(
                "authorization_code", KAKAO_API_KEY
                , "http://localhost:9000/oauth/kakao/token", code
//                , "https://prod.jaehwan.shop/oauth/kakao/login", code
        );
        System.out.println(authorization_code.getAccess_token());
        System.out.println(authorization_code.getExpires_in());
        return authorization_code.getAccess_token();
    }

    public TokensDto getAcc(String accessToken) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + accessToken);
        KakaoInfoRes kakaoInfoRes = kakaoKapiFeign.getUser(headerMap);

        String kakaoId = kakaoInfoRes.getId();

        Optional<User> findMember = userRepository.findByKakaoId(kakaoId);

        if (findMember.isPresent()) {
            return jwtService.createJwt(findMember.get().getId());
        } else {
            throw new ToJoinException(kakaoId);
        }
    }

    public TokensDto getKakaoTokens(String code) throws ToJoinException {
        // 카카오에서 토큰 받기
        KakaoToken kakaoToken = kakaoKauthFeign.getAccessToken(
                "authorization_code", KAKAO_API_KEY
                , "http://localhost:9000/onlyTestingLogin", code
//                , "https://prod.jaehwan.shop/oauth/kakao/login", code
        );

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + kakaoToken.getAccess_token());

        KakaoInfoRes kakaoInfoRes = kakaoKapiFeign.getUser(headerMap);
        String kakaoId = kakaoInfoRes.getId();

        Optional<User> findMember = userRepository.findByKakaoId(kakaoId);

        if (findMember.isPresent()) {
            return jwtService.createJwt(findMember.get().getId());
        } else {
            throw new ToJoinException(kakaoId);
        }
    }
}
