package DeliveryFriends.Backend.Controller;

import DeliveryFriends.Backend.Domain.Dto.Post.*;
import DeliveryFriends.Backend.Domain.Dto.Store.MenuInfoAndPriceDto;
import DeliveryFriends.Backend.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/team/make")
    public BaseResponse<String> addTeam(Principal principal, @RequestBody CreatePostReq createPostReq) {
        Long userId = Long.parseLong(principal.getName());
        postService.makeTeam(createPostReq, userId);
        return new BaseResponse<>("성공");
    }

    @GetMapping("/team/my")
    public BaseResponse<MyTeamRes> getMyTeam(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(postService.getMyTeam(userId));
    }

    @PostMapping("/team/join")
    public BaseResponse<String> getMyTeam(Principal principal, @RequestBody JoinPostReq joinPostReq) {
        Long userId = Long.parseLong(principal.getName());
        postService.joinTeam(joinPostReq, userId);
        return new BaseResponse<>("성공");
    }

    @GetMapping("/teamlist")
    public BaseResponse<List<TeamListRes>> getTeamList(Pageable pageable) {
        return new BaseResponse<>(postService.getTeamList(pageable));
    }

    @GetMapping("/teamlistMap")
    public BaseResponse<List<TeamListRes>> getTeamList(PostListReq postListReq) {
        return new BaseResponse<>(postService.getTeamListForMap(postListReq.getLessLatitude(), postListReq.getGreaterLatitude(), postListReq.getLessLongitude(), postListReq.getGreaterLongitude()));
    }

    @GetMapping("/teamlist/{teamId}")
    public BaseResponse<TeamRes> getTeamList(@PathVariable Long teamId) {
        return new BaseResponse<>(postService.getTeam(teamId));
    }

    @PostMapping("/team/cart")
    public BaseResponse<String> setCart(Principal principal, @RequestBody CartIdDto cartId) {
        Long userId = Long.parseLong(principal.getName());
        postService.setCart(cartId.getCartId(), userId);
        return new BaseResponse<>("성공");
    }

    @GetMapping("/team/status")
    public BaseResponse<List<TeamOrderStatusDto>> getTeamOrderStatus(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(postService.getTeamOrderStatus(userId));
    }

    // 대기 -> 결제 진행
    @PostMapping("/team/pay")
    public BaseResponse<Long> goPay(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(postService.goPay(userId));
    }

    // 결제 금액 확인
    @GetMapping("/team/payInfo")
    public BaseResponse<MenuInfoAndPriceDto> getMyPay(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(postService.getPayInfo(userId));
    }

    // 결제
    @PostMapping("/user/pay")
    public BaseResponse<String> setCart(Principal principal, @RequestBody OnlyKeyDto key) {
        Long userId = Long.parseLong(principal.getName());
        postService.pay(userId, key.getKey());
        return new BaseResponse<>("성공");
    }

    @PostMapping("/team/fail")
    public BaseResponse<String> failOrder(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return new BaseResponse<>(postService.failOrder(userId));
    }
}
