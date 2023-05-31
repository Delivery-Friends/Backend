package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.Post.*;
import DeliveryFriends.Backend.Service.JWTService;
import DeliveryFriends.Backend.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final JWTService jwtService;
    private final PostService postService;

    @PostMapping("/team/make")
    public void addTeam(@RequestBody CreatePostReq createPostReq) {
        Long userId = jwtService.getInfo();
        postService.makePost(createPostReq, userId);
    }

    @GetMapping("/team/my")
    public MyTeamRes getMyTeam() {
        Long userId = jwtService.getInfo();
        return postService.getMyTeam(userId);
    }

    @PostMapping("/team/join")
    public void getMyTeam(@RequestBody JoinPostReq joinPostReq) {
        Long userId = jwtService.getInfo();
        postService.joinPost(joinPostReq, userId);
    }

    @GetMapping("/team")
    public List<TeamRes> getTeamList(PostListReq postListReq) {
        return postService.getTeamList(postListReq.getLessLatitude(), postListReq.getGreaterLatitude(), postListReq.getLessLongitude(), postListReq.getGreaterLongitude());
    }

    @PostMapping("/team/cart")
    public void setCart(@RequestBody CartIdDto cartId) {
        Long userId = jwtService.getInfo();
        postService.setCart(cartId.getCartId(), userId);
    }

    @GetMapping("/pay")
    public void setCart(String key) {
        Long userId = jwtService.getInfo();
        postService.pay(userId, key);
    }
}
