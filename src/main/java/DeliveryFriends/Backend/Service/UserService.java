package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Domain.Dto.User.CreateUserReq;
import DeliveryFriends.Backend.Domain.Dto.TokensDto;
import DeliveryFriends.Backend.Repository.UserRepository;
import DeliveryFriends.Backend.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Transactional
    public TokensDto createUser(CreateUserReq req) throws BaseException {
        try {
            Optional<User> findUser = userRepository.findByEmail(req.getEmail());
            if(findUser.isPresent()) {
                throw new BaseException(EXISTS_EMAIL);
            }

            User user = userRepository.save(
                    User.builder()
                            .name(req.getName())
                            .nickname(req.getNickname())
                            .birth(req.getBirth())
                            .email(req.getEmail())
                            .password(passwordEncoder.encode(req.getPassword()))
                            .kakaoId(req.getKakaoId())
                            .gender(req.getGender())
                            .point(0L)
                            .build());

            return jwtService.createJwt(user.getId());

        } catch (BaseException e) {
            throw e;
        }
    }
}
