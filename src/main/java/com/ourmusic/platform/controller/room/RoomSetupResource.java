package com.ourmusic.platform.controller.room;

import com.ourmusic.platform.app.config.security.UserDetailsImpl;
import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.model.User;
import com.ourmusic.platform.service.room.RoomSetupService;
import com.ourmusic.platform.vo.request.room.RoomSetupVO;
import lindar.mrq.utils.user.UserWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@PreAuthorize("#oauth2.hasScope('read')")
@RequestMapping(Endpoints.ROOM.SETUP.ROOT)
@RequiredArgsConstructor
public class RoomSetupResource {

    private final RoomSetupService roomSetupService;

    @PostMapping
    public ResponseEntity<Void> createNewRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @RequestBody RoomSetupVO setupVO) {
        roomSetupService.createNewRoom(userDetails.getId(), setupVO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           String roomCode) {

    }

    @PostMapping(Endpoints.ROOM.SETUP.ACTIVATE)
    public ResponseEntity<Void> toggleActive(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                String roomCode) {

    }

    @PostMapping(Endpoints.ROOM.SETUP.TOGGLE_PLAY)
    public ResponseEntity<Boolean> togglePlayPause(@AuthenticationPrincipal UserDetailsImpl userDetails) {

    }


}
