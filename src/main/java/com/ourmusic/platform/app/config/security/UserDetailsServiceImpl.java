package com.ourmusic.platform.app.config.security;

import com.ourmusic.platform.model.User;
import com.ourmusic.platform.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<User> userEntity = userRepository.findById(username);

        if (userEntity.isPresent()) {
            final User user = userEntity.get();

            return new UserDetailsImpl(user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.isAccountNonExpired(),
                    user.isAccountNonLocked(),
                    user.isCredentialsNonExpired(),
                    user.isEnabled(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        }

        return null;
    }

//    private String passwordNoEncoding(AppUser appUser) {
//        // you can use one of bcrypt/noop/pbkdf2/scrypt/sha256
//        // more: https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-encoding
//        return "{noop}" + appUser.getUserPass();
//    }
}
