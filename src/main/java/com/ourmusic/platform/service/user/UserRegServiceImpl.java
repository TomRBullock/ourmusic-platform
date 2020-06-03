package com.ourmusic.platform.service.user;

import com.ourmusic.platform.model.User;
import com.ourmusic.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ourmusic.spotify.client.SpotifyClient;

@RequiredArgsConstructor
@Service
public class UserRegServiceImpl implements UserRegService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void createNewUser(String name, String password) {

        User user = new User();
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("user");

        userRepository.save(user);
    }

}
