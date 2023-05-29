package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Domain.*;
import DeliveryFriends.Backend.Domain.Dto.Post.CreatePostReq;
import DeliveryFriends.Backend.Domain.Dto.Post.JoinPostReq;
import DeliveryFriends.Backend.Domain.Dto.Post.TeamRes;
import DeliveryFriends.Backend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final StoreRepository storeRepository;
    private final TeamRepository teamRepository;
    private final TeamOrderRepository teamOrderRepository;

    // join -> wait -> order -> delivery
    // 커뮤니티 생성
    void makePost(CreatePostReq createPostReq, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        Optional<Cart> findCart = cartRepository.findById(createPostReq.getCartId());
        if (!findCart.isPresent()) {
            throw new BaseException(CANNOT_FOUND_CART);
        }
        Optional<Store> findStore = storeRepository.findById(createPostReq.getStoreId());
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }

        Team team = new Team(createPostReq.getEndTime(),
                findStore.get(), createPostReq.getMaxMember(),
                createPostReq.getBasicAddress(),
                createPostReq.getDetailedAddress(),
                createPostReq.getLatitude(),
                createPostReq.getLogitude()
        );

        TeamOrder teamOrder = new TeamOrder(true, "wait", team, findCart.get());
        findUser.get().setTeam(team);

        teamRepository.save(team);
        teamOrderRepository.save(teamOrder);
    }

    // 커뮤니티 가입
    void joinPost(JoinPostReq joinPostReq, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        Optional<Team> findTeam = teamRepository.findById(joinPostReq.getTeamId());
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_TEAM);
        }
        Team team = findTeam.get();
        User user = findUser.get();

        TeamOrder teamOrder = new TeamOrder(true, "join", team, null);
        teamOrderRepository.save(teamOrder);
        user.setTeam(team);
    }
}
