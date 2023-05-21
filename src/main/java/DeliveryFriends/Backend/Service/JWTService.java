package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Domain.User;
import DeliveryFriends.Backend.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class JWTService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity-in-milliseconds}")
    private Long accessTokenValidity;

    @Value("${jwt.refresh-token-validity-in-milliseconds}")
    private Long refreshTokenValidity;

    @Value("${KAKAO_API_KEY}")
    private String KAKAO_API_KEY;

    private final UserRepository userRepository;
    public TokensDto createJwt(long userId){
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userId",userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+ accessTokenValidity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userId",userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+ refreshTokenValidity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        User findUser = userRepository.findById(userId).get();
        findUser.setRefreshToken(refreshToken);

        return new TokensDto(accessToken, refreshToken);
    }

    /**
     * Header에서 X-ACCESS-TOKEN 으로 JWT 추출
     * @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /**
     * JWT에서 userId 추출
     * @return long
     * @throws BaseException EMPTY_JWT, INVALID_JWT
     */
    public Long getUserId() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("userId",Long.class);
    }
}
