package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.Post.*;
import DeliveryFriends.Backend.Service.JWTService;
import DeliveryFriends.Backend.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final JWTService jwtService;
    private final PostService postService;

    @PostMapping("/team/make")
    public void addTeam(Principal principal, @RequestBody CreatePostReq createPostReq) {
        Long userId = Long.parseLong(principal.getName());
        postService.makePost(createPostReq, userId);
    }

    @GetMapping("/team/my")
    public MyTeamRes getMyTeam(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return postService.getMyTeam(userId);
    }

    @PostMapping("/team/join")
    public void getMyTeam(Principal principal, @RequestBody JoinPostReq joinPostReq) {
        Long userId = Long.parseLong(principal.getName());
        postService.joinPost(joinPostReq, userId);
    }

    @GetMapping("/team")
    public List<TeamRes> getTeamList(PostListReq postListReq) {
        return postService.getTeamList(postListReq.getLessLatitude(), postListReq.getGreaterLatitude(), postListReq.getLessLongitude(), postListReq.getGreaterLongitude());
    }

    @PostMapping("/team/cart")
    public void setCart(Principal principal, @RequestBody CartIdDto cartId) {
        Long userId = Long.parseLong(principal.getName());
        postService.setCart(cartId.getCartId(), userId);
    }

    @GetMapping("/user/pay")
    public void setCart(Principal principal, String key) {
        Long userId = Long.parseLong(principal.getName());
        postService.pay(userId, key);
    }

    @GetMapping("/team/status")
    public List<TeamOrderStatusDto> getTeamOrderStatus(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return postService.getTeamOrderStatus(userId);
    }
}
