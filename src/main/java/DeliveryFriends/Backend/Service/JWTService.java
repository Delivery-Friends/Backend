package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Domain.User;
import DeliveryFriends.Backend.Repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class JWTService {

//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.access-token-validity-in-milliseconds}")
//    private Long accessTokenValidity;
//
//    @Value("${jwt.refresh-token-validity-in-milliseconds}")
//    private Long refreshTokenValidity;
//
//    @Value("${KAKAO_API_KEY}")
//    private String KAKAO_API_KEY;
//
//    private final UserRepository userRepository;
//
//    public TokensDto createJwt(long userId){
//        Date now = new Date();
//        String accessToken = Jwts.builder()
//                .setHeaderParam("type","jwt")
//                .claim("userId",userId)
//                .setIssuedAt(now)
//                .setExpiration(new Date(System.currentTimeMillis()+ accessTokenValidity))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//
//        String refreshToken = Jwts.builder()
//                .setHeaderParam("type","jwt")
//                .claim("userId",userId)
//                .setIssuedAt(now)
//                .setExpiration(new Date(System.currentTimeMillis()+ refreshTokenValidity))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//        User findUser = userRepository.findById(userId).get();
//        findUser.setRefreshToken(refreshToken);
//
//        return new TokensDto(accessToken, refreshToken);
//    }
//
//    public TokensDto doRefresh() {
//        String refreshToken = getRefreshToken();
//
//        Jws<Claims> claims;
//        try{
//            claims = Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(refreshToken);
//        } catch (ExpiredJwtException e) { // 만료
//            throw new BaseException(EXPIRED_JWT);
//        } catch (Exception ignored) { // 변조
//            throw new BaseException(INVALID_JWT);
//        }
//
//        Long userId = getUserId(refreshToken);
//        Optional<User> findUser = userRepository.findById(userId);
//        if (!findUser.isPresent()) {
//            throw new BaseException(INVALID_JWT);
//        }
//        if (!refreshToken.equals(findUser.get().getRefreshToken())) {
//            throw new BaseException(INVALID_JWT);
//        }
//        return createJwt(userId);
//    }
//
//    public String getAccessToken(){
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        return request.getHeader("ACCESS-TOKEN");
//    }
//
//    public String getRefreshToken(){
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        return request.getHeader("REFRESH-TOKEN");
//    }
//
//    public Long getInfo() throws BaseException {
//        String token = getAccessToken();
//
//        Jws<Claims> claims;
//        try{
//            claims = Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token);
//        } catch (ExpiredJwtException e) { // 만료
//            throw new BaseException(EXPIRED_JWT);
//        } catch (Exception ignored) { // 변조
//            throw new BaseException(INVALID_JWT);
//        }
//
//        return claims.getBody().get("userId",Long.class);
//    }
//
//    public Long getUserId(String token) throws BaseException {
//        Jws<Claims> claims;
//        try{
//            claims = Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token);
//        } catch (Exception ignored) {
//            throw new BaseException(INVALID_JWT);
//        }
//
//        return claims.getBody().get("userId",Long.class);
//    }
}
