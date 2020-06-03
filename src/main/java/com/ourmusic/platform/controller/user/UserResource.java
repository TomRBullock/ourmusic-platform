package com.ourmusic.platform.controller.user;

import com.ourmusic.platform.app.config.security.UserDetailsImpl;
import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.service.user.UserRegServiceImpl;
import com.ourmusic.platform.vo.UserBasicVO;
import com.ourmusic.platform.vo.request.user.UserRegistrationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.USER.ROOT)
public class UserResource {

    private final UserRegServiceImpl userService;

    @PostMapping(Endpoints.USER.CREATE)
    public ResponseEntity<Void> createNewUser(@RequestBody UserRegistrationVO userRegistrationVO) {
        userService.createNewUser(userRegistrationVO.getUsername(), userRegistrationVO.getPassword());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping()
    public ResponseEntity<UserBasicVO> getAuthedUsername(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserBasicVO userBasicVO = new UserBasicVO();
//        userBasicVO.setId(userDetails.getId());
        userBasicVO.setUsername(userDetails.getUsername());
        return ResponseEntity.ok(userBasicVO);
    }

}
