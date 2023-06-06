package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Controller.feign.TossApiFeign;
import DeliveryFriends.Backend.Domain.*;
import DeliveryFriends.Backend.Domain.Dto.FilenameDto;
import DeliveryFriends.Backend.Domain.Dto.Post.*;
import DeliveryFriends.Backend.Domain.Dto.Store.MenuInfoAndPriceDto;
import DeliveryFriends.Backend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final UserOrderRepository userOrderRepository;
    private final ChoiceMenuRepository choiceMenuRepository;
    private final ChoiceOptionRepository choiceOptionRepository;

    private final TossApiFeign tossApiFeign;

    @Value("${TOSS_API_KEY}")
    private String tossKey;

    // join	->	wait	->	pay	->	order	->	delivery	->	complete
    // 가입	->	결제진행 -> 	결제완료	-> 	주문진행	->	배달시작	->	완료

    // 커뮤니티 생성
    public void makeTeam(CreatePostReq createPostReq, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        if (user.getTeam() != null) {
            Team beforeTeam = user.getTeam();
            TeamOrder beforeTeamOrder = teamOrderRepository.findByTeamAndUser(beforeTeam, user).get();
            if (beforeTeamOrder.getOrderStatus().equals("join") && beforeTeam.getGroupEndTime().isBefore(LocalDateTime.now())) {
                // 가입한 팀 존재
                throw new BaseException(ALREADY_JOIN_TEAM);
            } else if (beforeTeamOrder.getOrderStatus().equals("wait") || beforeTeamOrder.getOrderStatus().equals("pay") || beforeTeamOrder.getOrderStatus().equals("order") || beforeTeamOrder.getOrderStatus().equals("delivery")) {
                // 진행 중인 팀
                throw new BaseException(ALREADY_PROGRESS_TEAM);
            }
        }

        Optional<Store> findStore = storeRepository.findById(createPostReq.getStoreId());
        if (!findStore.isPresent()) {
            throw new BaseException(CANNOT_FOUND_STORE);
        }

        Team team = new Team(
                createPostReq.getEndTime(),
                findStore.get(),
                user.getName(),
                user.getImgSrc(),
                user.getId(),
                createPostReq.getMaxMember(),
                createPostReq.getBasicAddress(),
                createPostReq.getDetailedAddress(),
                createPostReq.getLatitude(),
                createPostReq.getLogitude(),
                null,
                "join"
        );

        TeamOrder teamOrder = new TeamOrder(true, "join", team, user, null);
        user.setTeam(team);

        teamRepository.save(team);
        teamOrderRepository.save(teamOrder);
    }

    // 커뮤니티 가입
    public void joinTeam(JoinPostReq joinPostReq, Long userId) {
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
        if(!team.getOrderStatus().equals("join")) {
            throw new BaseException(ALREADY_PROGRESS_TEAM);
        }

        if (user.getTeam() != null) {
            Team beforeTeam = user.getTeam();
            TeamOrder beforeTeamOrder = teamOrderRepository.findByTeamAndUser(beforeTeam, user).get();
            if (beforeTeamOrder.getOrderStatus().equals("join") && beforeTeam.getGroupEndTime().isBefore(LocalDateTime.now())) {
                // 가입한 팀 존재
                throw new BaseException(ALREADY_JOIN_TEAM);
            } else if (beforeTeamOrder.getOrderStatus().equals("wait") || beforeTeamOrder.getOrderStatus().equals("pay") || beforeTeamOrder.getOrderStatus().equals("order") || beforeTeamOrder.getOrderStatus().equals("delivery")) {
                // 진행 중인 팀
                throw new BaseException(ALREADY_PROGRESS_TEAM);
            }
        }

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
                    (float) (team.getStore().getReviewScore()) / (float) (team.getStore().getReviewCount()),
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
    public List<TeamListRes> getTeamList(Pageable pageable) {
        Page<Team> findTeams = teamRepository.findAll(pageable);

        List<TeamListRes> result = new ArrayList<>();
        for (Team team : findTeams) {

            List<FilenameDto> storeMedium = storeMediaRepository.getStoreMedium(team.getStore().getId());
            List<String> medium = new ArrayList<>();
            for (FilenameDto filenameDto : storeMedium) {
                medium.add(filenameDto.getFilename());
            }
            Long nowMember = userRepository.countByTeam(team);
            if (team.getStore().getReviewCount() != 0) {
                TeamListRes teamListRes = new TeamListRes(
                        team.getGroupEndTime(),
                        team.getId(),
                        team.getStore().getId(),
                        team.getStore().getName(),
                        team.getLeaderName(),
                        team.getStore().getCategory(),
                        medium,
                        (float) (team.getStore().getReviewScore()) / (float) (team.getStore().getReviewCount()),
                        team.getStore().getReviewCount(),
                        team.getMaxMember(),
                        nowMember,
                        team.getBasicAddress(),
                        team.getLatitude(),
                        team.getLongitude()
                );
                result.add(teamListRes);
            } else {
                TeamListRes teamListRes = new TeamListRes(
                        team.getGroupEndTime(),
                        team.getId(),
                        team.getStore().getId(),
                        team.getStore().getName(),
                        team.getLeaderName(),
                        team.getStore().getCategory(),
                        medium,
                        0F,
                        team.getStore().getReviewCount(),
                        team.getMaxMember(),
                        nowMember,
                        team.getBasicAddress(),
                        team.getLatitude(),
                        team.getLongitude()
                );
                result.add(teamListRes);
            }
        }
        return result;
    }

    // 팀리스트 조회
    public List<TeamListRes> getTeamListForMap(String lessLatitude, String greaterLatitude, String lessLongitude, String greaterLongitude) {
        List<Team> findTeams = teamRepository.findByGroupEndTimeAfterAndLatitudeGreaterThanEqualAndLatitudeLessThanEqualAndLongitudeGreaterThanEqualAndLongitudeLessThanEqual(LocalDateTime.now(), lessLatitude, greaterLatitude, lessLongitude, greaterLongitude);

        List<TeamListRes> result = new ArrayList<>();
        for (Team team : findTeams) {

            List<FilenameDto> storeMedium = storeMediaRepository.getStoreMedium(team.getStore().getId());
            List<String> medium = new ArrayList<>();
            for (FilenameDto filenameDto : storeMedium) {
                medium.add(filenameDto.getFilename());
            }
            Long nowMember = userRepository.countByTeam(team);
            if (team.getStore().getReviewCount() != 0) {
                TeamListRes teamListRes = new TeamListRes(
                    team.getGroupEndTime(),
                    team.getId(),
                    team.getStore().getId(),
                    team.getStore().getName(),
                    team.getLeaderName(),
                    team.getStore().getCategory(),
                    medium,
                    (float) (team.getStore().getReviewScore()) / (float) (team.getStore().getReviewCount()),
                    team.getStore().getReviewCount(),
                    team.getMaxMember(),
                    nowMember,
                    team.getBasicAddress(),
                    team.getLatitude(),
                    team.getLongitude()
                );
                result.add(teamListRes);
            } else {
                TeamListRes teamListRes = new TeamListRes(
                    team.getGroupEndTime(),
                    team.getId(),
                    team.getStore().getId(),
                    team.getStore().getName(),
                    team.getLeaderName(),
                    team.getStore().getCategory(),
                    medium,
                    0F,
                    team.getStore().getReviewCount(),
                    team.getMaxMember(),
                    nowMember,
                    team.getBasicAddress(),
                    team.getLatitude(),
                    team.getLongitude()
                );
                result.add(teamListRes);
            }
        }
        return result;
    }

    public TeamRes getTeam(Long teamId) {
        Optional<Team> findTeam = teamRepository.findById(teamId);
        Team team = findTeam.get();
        List<FilenameDto> storeMedium = storeMediaRepository.getStoreMedium(team.getStore().getId());
        List<String> medium = new ArrayList<>();
        for (FilenameDto filenameDto : storeMedium) {
            medium.add(filenameDto.getFilename());
        }
        Long nowMember = userRepository.countByTeam(team);

        if (team.getStore().getReviewCount() != 0) {
            return new TeamRes(
                team.getGroupEndTime(),
                team.getId(),
                team.getStore().getId(),
                team.getStore().getName(),
                team.getLeaderId(),
                team.getLeaderName(),
                team.getLeaderImgSrc(),
                team.getStore().getCategory(),
                medium,
                (float) (team.getStore().getReviewScore()) / (float) (team.getStore().getReviewCount()),
                team.getStore().getReviewCount(),
                team.getStore().getDeliveryWaitTime(),
                team.getStore().getDeliveryTip(),
                team.getStore().getMinPrice(),

                team.getMaxMember(),
                nowMember,

                team.getBasicAddress(),
                team.getDetailedAddress(),

                team.getLatitude(),
                team.getLongitude()
            );
        } else {
            return new TeamRes(
                    team.getGroupEndTime(),
                    team.getId(),
                    team.getStore().getId(),
                    team.getStore().getName(),
                    team.getLeaderId(),
                    team.getLeaderName(),
                    team.getLeaderImgSrc(),
                    team.getStore().getCategory(),
                    medium,
                    0F,
                    team.getStore().getReviewCount(),
                    team.getStore().getDeliveryWaitTime(),
                    team.getStore().getDeliveryTip(),
                    team.getStore().getMinPrice(),

                    team.getMaxMember(),
                    nowMember,

                    team.getBasicAddress(),
                    team.getDetailedAddress(),

                    team.getLatitude(),
                    team.getLongitude()
            );
        }
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

    public Long goPay(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();

        Team team = user.getTeam();
        if (!team.getLeaderId().equals(userId)) {
            throw new BaseException(NOT_LEADER);
        }

        List<User> members = userRepository.findByTeam(team);
        for (User member : members) {
            Optional<TeamOrder> findMemberOrder = teamOrderRepository.findByTeamAndUser(team, member);
            TeamOrder teamOrder = findMemberOrder.get();
            if (teamOrder.getCart() == null) {
                throw new BaseException(CART_NOT_SETTING);
            }
            teamOrder.setOrderStatus("wait");
        }
        team.setOrderStatus("wait");

        Long deliveryTip = team.getStore().getDeliveryTip();
        int count = members.size();
        Long divideTip = deliveryTip / count;
        while (divideTip * count > deliveryTip) {
            divideTip += 10L;
        }
        team.setDivideTip(divideTip);
        return divideTip;
    }

    // 결제 금액 반환
    public MenuInfoAndPriceDto getPayInfo(Long userId) {
        String menuInfo = "";
        Long price = 0L;
        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        Team team = user.getTeam();
        Optional<TeamOrder> findTeamOrder = teamOrderRepository.findByTeamAndUser(team, user);
        if (!findTeamOrder.isPresent()) {
            throw new BaseException(CANNOT_FOUND_TEAM_ORDER);
        }
        TeamOrder teamOrder = findTeamOrder.get();
        if (teamOrder.getCart() == null) {
            throw new BaseException(CART_NOT_SETTING);
        }
        Long deliveryTip = team.getDivideTip();

        Cart cart = teamOrder.getCart();
        List<ChoiceMenu> choiceMenus = choiceMenuRepository.findByCart(cart);
        //가게의 메뉴
        for (ChoiceMenu choiceMenu : choiceMenus) {
            menuInfo = menuInfo + choiceMenu.getMenu().getName() + " " + choiceMenu.getCount() + "개";
            price = price + (choiceMenu.getMenu().getPrice() * choiceMenu.getCount());

            //가게의 옵션
            List<ChoiceOption> choiceOptions = choiceOptionRepository.findByChoiceMenu(choiceMenu);
            if(choiceOptions.size() > 1) {
                menuInfo += "( ";
            }
            for (ChoiceOption choiceOption : choiceOptions) {
                menuInfo = menuInfo + choiceOption.getMenuOption().getName() + " " + choiceOption.getCount() + "개 ";
                price = price + (choiceOption.getMenuOption().getPrice() * choiceOption.getCount());
            }
            if(choiceOptions.size() > 1) {
                menuInfo += ")";
            }
            menuInfo += " ";
        }
        return new MenuInfoAndPriceDto(teamOrder.getId(), user.getName(), menuInfo, price, deliveryTip);
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
        MenuInfoAndPriceDto payInfo = getPayInfo(userId);
        UserOrder userOrder = new UserOrder(user, paymentKey, team.getStore(), "개인 결제 완료", team, payInfo.getMenuInfo(), payInfo.getPrice(), payInfo.getDeliveryTip());
        userOrderRepository.save(userOrder);

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
            TeamAndMembersDto teamAndMembersDto = new TeamAndMembersDto(team, members);
            teamPayToOrder(teamAndMembersDto);
            team.getStore().setOrderCount(team.getStore().getOrderCount() + members.size());
        }
        return "성공";
    }

    private void teamPayToOrder(TeamAndMembersDto teamAndMembersDto) {
        List<User> members = teamAndMembersDto.getMembers();
        Team team = teamAndMembersDto.getTeam();

        for (User member : members) {
            Optional<TeamOrder> findMemberTeamOrder = teamOrderRepository.findByTeamAndUser(team, member);
            if (!findMemberTeamOrder.isPresent()) {
                throw new BaseException(CANNOT_FOUND_TEAM_ORDER);
            }
            TeamOrder memberTeamOrder = findMemberTeamOrder.get();
            // order 상태가 맞으나 현재 관리자 페이지가 없는 관계로 complete 처리
            memberTeamOrder.setOrderStatus("complete");
            Optional<UserOrder> findUserOrder = userOrderRepository.findByUserAndTeam(member, team);
            if (!findUserOrder.isPresent()) {
                throw new BaseException(CANNOT_FOUND_USER_ORDER);
            }
            UserOrder memberUserOrder = findUserOrder.get();
            memberUserOrder.setOrderInfo("팀 결제 완료");
        }
        // order 상태가 맞으나 현재 관리자 페이지가 없는 관계로 complete 처리
        team.setOrderStatus("complete");
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
            result.add(new TeamOrderStatusDto(member.getNickname(), findTeamOrder.get().getOrderStatus()));
        }
        return result;
    }

    public String failOrder(Long userId) {

        Optional<User> findUser = userRepository.findById(userId);
        if (!findUser.isPresent()) {
            throw new BaseException(CANNOT_FOUND_USER);
        }
        User user = findUser.get();
        Team team = user.getTeam();
        if (!team.getLeaderId().equals(userId)) {
                throw new BaseException(NOT_LEADER);
        }

        List<User> members = userRepository.findByTeam(team);
        for (User member : members) {
            Optional<TeamOrder> findMemberTeamOrder = teamOrderRepository.findByTeamAndUser(team, member);
            if (!findMemberTeamOrder.isPresent()) {
                throw new BaseException(CANNOT_FOUND_TEAM_ORDER);
            }
            if (findMemberTeamOrder.get().getOrderStatus().equals("wait") || findMemberTeamOrder.get().getOrderStatus().equals("pay")) {
                TeamOrder memberOrder = findMemberTeamOrder.get();
                memberOrder.setOrderStatus("join");
            } else {
                throw new BaseException(ORDER_PROGRESS_ERROR);
            }
            Optional<UserOrder> findUserOrder = userOrderRepository.findByUserAndTeam(member, team);
            if (!findUserOrder.isPresent()) {
                throw new BaseException(CANNOT_FOUND_USER_ORDER);
            }
            UserOrder userOrder = findUserOrder.get();
            userOrder.setOrderInfo("결제 취소");
        }
        return "성공";
    }
}
