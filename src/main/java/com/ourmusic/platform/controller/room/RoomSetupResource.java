package com.ourmusic.platform.controller.room;

import com.ourmusic.platform.app.config.security.UserDetailsImpl;
import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.service.room.RoomSetupService;
import com.ourmusic.platform.vo.request.room.RoomSetupVO;
import lindar.mrq.utils.user.UserWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(Endpoints.ROOM.ROOT + Endpoints.ROOM.SETUP)
@RequiredArgsConstructor
public class RoomSetupResource {

    private final RoomSetupService roomSetupService;

    @PostMapping
    public ResponseEntity<Void> createNewRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, RoomSetupVO setupVO) {
        roomSetupService.createNewRoom(userDetails.getId(), setupVO);
        return ResponseEntity.ok().build();
    }

}
