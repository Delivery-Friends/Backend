package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Controller.feign.TossApiFeign;
import DeliveryFriends.Backend.Domain.*;
import DeliveryFriends.Backend.Domain.Dto.FilenameDto;
import DeliveryFriends.Backend.Domain.Dto.Kakao.TossRes;
import DeliveryFriends.Backend.Domain.Dto.Post.*;
import DeliveryFriends.Backend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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
    private final StoreMediaRepository storeMediaRepository;

    private final TossApiFeign tossApiFeign;

    @Value("${TOSS_API_KEY}")
    private String tossKey;

    // join -> wait -> pay -> order -> delivery
    // 커뮤니티 생성
    public void makePost(CreatePostReq createPostReq, Long userId) {
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

        TeamOrder teamOrder = new TeamOrder(true, "wait", team, findUser.get(), findCart.get());
        findUser.get().setTeam(team);

        teamRepository.save(team);
        teamOrderRepository.save(teamOrder);
    }

    // 커뮤니티 가입
    public void joinPost(JoinPostReq joinPostReq, Long userId) {
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

        TeamOrder teamOrder = new TeamOrder(true, "join", team, user, null);
        teamOrderRepository.save(teamOrder);
        user.setTeam(team);
    }

    //현재 팀 정보 조회
    public MyTeamRes getMyTeam(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        Team team = findUser.get().getTeam();
        List<FilenameDto> storeMedium = storeMediaRepository.getStoreMedium(team.getStore().getId());
        List<String> medium = new ArrayList<>();
        for (FilenameDto filenameDto : storeMedium) {
            medium.add(filenameDto.getFilename());
        }

        if (team.getStore().getReviewCount() != 0) {
            return new MyTeamRes(
                    team.getStore().getId(),
                    team.getStore().getName(),
                    (float) ((team.getStore().getReviewScore()) / (team.getStore().getReviewCount())),
                    team.getStore().getReviewCount(),
                    team.getBasicAddress(),
                    team.getDetailedAddress(),
                    team.getLongitude(),
                    team.getLatitude(),
                    team.getGroupEndTime(),
                    medium
            );
        } else {
            return new MyTeamRes(
                    team.getStore().getId(),
                    team.getStore().getName(),
                    (0F),
                    team.getStore().getReviewCount(),
                    team.getBasicAddress(),
                    team.getDetailedAddress(),
                    team.getLongitude(),
                    team.getLatitude(),
                    team.getGroupEndTime(),
                    medium
            );
        }
    }

    // 팀리스트 조회
    public List<TeamRes> getTeamList(String lessLatitude, String greaterLatitude, String lessLongitude, String greaterLongitude) {
        List<Team> findTeams = teamRepository.findByGroupEndTimeAfterAndLatitudeGreaterThanEqualAndLatitudeLessThanEqualAndLongitudeGreaterThanEqualAndLongitudeLessThanEqual(LocalDateTime.now(), lessLatitude, greaterLatitude, lessLongitude, greaterLongitude);

        System.out.println("@@" + findTeams.size());
        List<TeamRes> result = new ArrayList<>();
        for (Team team : findTeams) {

            List<FilenameDto> storeMedium = storeMediaRepository.getStoreMedium(team.getStore().getId());
            List<String> medium = new ArrayList<>();
            for (FilenameDto filenameDto : storeMedium) {
                medium.add(filenameDto.getFilename());
            }
            if (team.getStore().getReviewCount() != 0) {
                TeamRes teamRes = new TeamRes(
                        team.getGroupEndTime(),
                        team.getStore().getId(),
                        team.getStore().getName(),
                        medium,
                        (float) ((team.getStore().getReviewScore()) / (team.getStore().getReviewCount())),
                        team.getStore().getReviewCount(),
                        team.getStore().getDeliveryWaitTime(),
                        team.getStore().getDeliveryTip(),
                        team.getStore().getMinPrice(),
                        team.getMaxMember(),
                        team.getBasicAddress(),
                        team.getDetailedAddress(),
                        team.getLatitude(),
                        team.getLongitude()
                );
                result.add(teamRes);
            } else {
                TeamRes teamRes = new TeamRes(
                        team.getGroupEndTime(),
                        team.getStore().getId(),
                        team.getStore().getName(),
                        medium,
                        0F,
                        team.getStore().getReviewCount(),
                        team.getStore().getDeliveryWaitTime(),
                        team.getStore().getDeliveryTip(),
                        team.getStore().getMinPrice(),
                        team.getMaxMember(),
                        team.getBasicAddress(),
                        team.getDetailedAddress(),
                        team.getLatitude(),
                        team.getLongitude()
                );
                result.add(teamRes);
            }
        }
        return result;
    }

    // 커뮤니티 카트 설정
    public void setCart(Long cartId, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        Optional<Cart> findCart = cartRepository.findById(cartId);
        if (!findCart.isPresent()) {
            throw new BaseException(CANNOT_FOUND_CART);
        }
        Cart cart = findCart.get();
        User user = findUser.get();
        Team team = user.getTeam();
        if (!cart.getStore().getId().equals(team.getStore().getId())) {
            throw new BaseException(NOT_MATCH_STORE);
        }

        Optional<TeamOrder> findTeamOrder = teamOrderRepository.findByTeamAndUser(team, user);
        if (!findTeamOrder.isPresent()) {
            throw new BaseException(CANNOT_FOUND_TEAM_ORDER);
        }
        findTeamOrder.get().setCart(cart);
    }

    // 결제완료
    public String pay(Long userId, String paymentKey) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + tossKey);

        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        Team team = user.getTeam();

        /** 테스트 결제이므로 결제 확인 불가능 -> 결제 성공 가정
         * TossRes str = tossApiFeign.getPayInfo(paymentKey, headerMap);
         */

        Optional<TeamOrder> findTeamOrder = teamOrderRepository.findByTeamAndUser(team, user);
        if (!findTeamOrder.isPresent()) {
            throw new BaseException(CANNOT_FOUND_TEAM_ORDER);
        }
        TeamOrder teamOrder = findTeamOrder.get();
        teamOrder.setOrderStatus("pay");
        UserOrder userOrder = new UserOrder();
        List<User> members = userRepository.findByTeam(team);
        Boolean check = true;
        for (User member : members) {
            Optional<TeamOrder> findMemberTeamOrder = teamOrderRepository.findByTeamAndUser(team, member);
            if (!findMemberTeamOrder.isPresent()) {
                throw new BaseException(CANNOT_FOUND_TEAM_ORDER);
            }
            TeamOrder memberOrder = findMemberTeamOrder.get();
            if (!memberOrder.getOrderStatus().equals("pay")) {
                check = false;
                break;
            }
        }
        // 모두 결제 완료한 상태 -> order
        if (check) {
            for (User member : members) {
                Optional<TeamOrder> findMemberTeamOrder = teamOrderRepository.findByTeamAndUser(team, member);
                if (!findMemberTeamOrder.isPresent()) {
                    throw new BaseException(CANNOT_FOUND_TEAM_ORDER);
                }
                TeamOrder memberOrder = findMemberTeamOrder.get();
                memberOrder.setOrderStatus("order");
            }


        }
    }

    public List<TeamOrderStatusDto> getTeamOrderStatus(Long userId){

        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        Team team = findUser.get().getTeam();
        List<User> members = userRepository.findByTeam(team);
        List<TeamOrderStatusDto> result = new ArrayList<>();
        for (User member : members) {
            Optional<TeamOrder> findTeamOrder = teamOrderRepository.findByTeamAndUser(team, member);
            if (!findTeamOrder.isPresent()) {
                throw new BaseException(CANNOT_FOUND_TEAM_ORDER);
            }
            result.add(new TeamOrderStatusDto(member.getNickname(), findTeamOrder.get().getOrderStatus());
        }
        return result;
    }


}
