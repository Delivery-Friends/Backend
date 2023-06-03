package DeliveryFriends.Backend.Config.Security;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static DeliveryFriends.Backend.Controller.BaseResponseStatus.CANNOT_FOUND_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        log.info("user details at username = {}", id);
        return userRepository.findById(Long.valueOf(id))
                .map(user -> createUser(user))
                .orElseThrow(() -> new UsernameNotFoundException(id + "Not Found"));

//        return createUser(user);
    }

    /**Security User 정보를 생성한다. */
    private User createUser(DeliveryFriends.Backend.Domain.User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());

        User result =  new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getId()),
                user.getPassword(),
                Collections.singletonList(grantedAuthority)
        );

        return result;
    }
}
