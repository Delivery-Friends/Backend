package DeliveryFriends.Backend.Controller.feign;

import DeliveryFriends.Backend.Domain.Dto.Kakao.KakaoInfoRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name="kapi", url="https://kapi.kakao.com" )
public interface KakaoKapiFeign {

    @GetMapping("/v2/user/me")
    KakaoInfoRes getUser(@RequestHeader Map<String, String> header);
}
