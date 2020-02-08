package com.ourmusic.platform.controller.user;

import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.USER.ROOT)
public class UserResource {

    private final UserService userService;

    @PostMapping(Endpoints.USER.CREATE)
    public void createNewUser() {
        userService.createNewUser("Tom");
    }

}
