package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Config.Security.TokenProvider;
import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Controller.ToJoinException;
import DeliveryFriends.Backend.Controller.feign.KakaoKapiFeign;
import DeliveryFriends.Backend.Controller.feign.KakaoKauthFeign;
import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoInfoRes;
import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoToken;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Domain.Dto.User.CreateUserReq;
import DeliveryFriends.Backend.Domain.Dto.User.CreateUserRes;
import DeliveryFriends.Backend.Domain.User;
import DeliveryFriends.Backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.Bad_Request;
import static DeliveryFriends.Backend.Controller.BaseResponseStatus.EXISTS_KAKAOID;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthService {

    private final UserRepository userRepository;
    private final KakaoKauthFeign kakaoKauthFeign;
    private final KakaoKapiFeign kakaoKapiFeign;
    private final JWTService jwtService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    @Value("${KAKAO_API_KEY}")
    private String KAKAO_API_KEY;

    public String getKakaoAccessToken(String code) {
        KakaoToken authorization_code = kakaoKauthFeign.getAccessToken(
                "authorization_code", KAKAO_API_KEY
                , "http://localhost:9000/api/getKakaoAccessToken", code
//                , "https://prod.jaehwan.shop/oauth/kakao/login", code
        );
        return authorization_code.getAccess_token();
    }

    public CreateUserRes createUser(CreateUserReq req) throws BaseException {
        if (!StringUtils.hasText(req.getKakaoId()) || !StringUtils.hasText(req.getNickname()) || !StringUtils.hasText(req.getName())) {
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
                            .imgSrc("https://delivery-friends-s3-bucket.s3.ap-northeast-2.amazonaws.com/test/d8842425-7255-48f5-8c0a-bc693706476d%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202023-02-14%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%202.29.08.png")
                            .role("USER")
                            .password(passwordEncoder.encode("123123"))
                            .build());

            return new CreateUserRes(user.getName(), user.getNickname());

        } catch (BaseException e) {
            throw e;
        }
    }

    public TokensDto getTokensByKakaoToken(String accessToken) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + accessToken);
        KakaoInfoRes kakaoInfoRes = kakaoKapiFeign.getUser(headerMap);

        String kakaoId = kakaoInfoRes.getId();

        Optional<User> findMember = userRepository.findByKakaoId(kakaoId);

        if (findMember.isPresent()) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(findMember.get().getId(), "123123");
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            return tokenProvider.createToken(authentication);
        } else {
            throw new ToJoinException(kakaoId);
        }
    }

    public TokensDto getKakaoTokens(String code) throws ToJoinException {
        // 카카오에서 토큰 받기
        KakaoToken kakaoToken = kakaoKauthFeign.getAccessToken(
                "authorization_code", KAKAO_API_KEY
                , "http://localhost:9000/api/onlyTestingLogin", code
//                , "https://prod.jaehwan.shop/oauth/kakao/login", code
        );

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + kakaoToken.getAccess_token());

        KakaoInfoRes kakaoInfoRes = kakaoKapiFeign.getUser(headerMap);
        String kakaoId = kakaoInfoRes.getId();

        Optional<User> findMember = userRepository.findByKakaoId(kakaoId);

        if (findMember.isPresent()) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(findMember.get().getId(), "123123");
            AuthenticationManager object = authenticationManagerBuilder.getObject();
            Authentication authentication = object.authenticate(authenticationToken);

            return tokenProvider.createToken(authentication);
//            return jwtService.createJwt(findMember.get().getId());
        } else {
            throw new ToJoinException(kakaoId);
        }
    }
}
