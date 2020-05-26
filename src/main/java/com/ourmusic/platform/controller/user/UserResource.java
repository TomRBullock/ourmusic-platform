package com.ourmusic.platform.controller.user;

import com.ourmusic.platform.app.config.security.UserDetailsImpl;
import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.model.User;
import com.ourmusic.platform.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.security.Principal;
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

    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping()
    public ResponseEntity<String> getAuthedUsername(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userDetails.getId());
    }

}
