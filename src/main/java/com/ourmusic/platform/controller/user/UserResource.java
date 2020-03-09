package com.ourmusic.platform.controller.user;

import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.security.Security;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.USER.ROOT)
public class UserResource {

    private final UserService userService;

    @PostMapping(Endpoints.USER.CREATE)
    public void createNewUser() {
        userService.createNewUser("test", "test");
    }

    @GetMapping()
    public ResponseEntity<String> getAuthedUsername() {
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(authenticated.getName());
    }

}
