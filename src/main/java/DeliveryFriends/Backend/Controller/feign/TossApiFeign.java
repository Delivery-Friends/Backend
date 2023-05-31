package DeliveryFriends.Backend.Controller.feign;

import DeliveryFriends.Backend.Domain.Dto.Kakao.TossRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name="api", url="https://api.tosspayments.com" )
public interface TossApiFeign {

    @GetMapping("/v1/payments/{paymentKey}")
    TossRes getPayInfo(
            @PathVariable(value = "paymentKey") String paymentKey,
            @RequestHeader Map<String, String> header
    );
}