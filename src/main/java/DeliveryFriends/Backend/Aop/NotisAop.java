package DeliveryFriends.Backend.Aop;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Controller.BaseResponseStatus;
import DeliveryFriends.Backend.Domain.Dto.Post.TeamAndMembersDto;
import DeliveryFriends.Backend.Domain.Noti;
import DeliveryFriends.Backend.Domain.Team;
import DeliveryFriends.Backend.Domain.User;
import DeliveryFriends.Backend.Repository.NotiRepository;
import DeliveryFriends.Backend.Repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@Aspect
@Component
@RequiredArgsConstructor
public class NotisAop {

    private final NotiRepository notiRepository;

    @Around("execution(* DeliveryFriends.Backend.Service.PostService.teamPayToOrder*(..))")
    public void payToOrderNoti(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        TeamAndMembersDto req = null;
        for (Object a : args) {
            req = (TeamAndMembersDto) a;
        }
        Team team = req.getTeam();
        List<User> members = req.getMembers();

        for (User member : members) {
            Noti noti = new Noti(member, "팀 결제 완료", "배프 팀 " + team.getLeaderName() + "의 모든 인원이 모든 인원이 결제를 완료했습니다.");
            notiRepository.save(noti);
        }
        
        joinPoint.proceed();
    }
}
