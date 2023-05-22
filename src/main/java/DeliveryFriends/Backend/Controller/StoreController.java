package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
}
