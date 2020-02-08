package com.ourmusic.platform.service.user;

import com.ourmusic.platform.model.User;
import com.ourmusic.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void createNewUser(String name) {

        User user = new User();
        user.setFirstName(name);

        userRepository.save(user);
    }

}
