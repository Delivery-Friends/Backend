package DeliveryFriends.Backend.Controller.feign;

import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kauth", url="https://kauth.kakao.com")
public interface KakaoKauthFeign {
    @GetMapping("/oauth/token")
    KakaoToken getAccessToken(
            @RequestParam("grant_type") String grant_type,
            @RequestParam("client_id") String client_id,
            @RequestParam("redirect_uri") String redirect_uri,
            @RequestParam("code") String code
    );
}
