package com.ourmusic.platform.controller.room;

import com.ourmusic.platform.app.config.security.UserDetailsImpl;
import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.service.room.RoomAdminService;
import com.ourmusic.platform.vo.request.room.RoomVO;
import com.ourmusic.platform.vo.request.room.RoomSetupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("#oauth2.hasScope('read')")
@RequestMapping(Endpoints.ROOM.ADMIN.ROOT)
@RequiredArgsConstructor
public class RoomAdminResource {

    private final RoomAdminService roomAdminService;

    @PostMapping
    public ResponseEntity<Void> createNewRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @RequestBody RoomSetupVO setupVO) {
        if (setupVO.getRoomName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        roomAdminService.createNewRoom(userDetails.getId(), setupVO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @RequestParam String roomId) {
        return ResponseEntity.ok(roomAdminService.deleteRoom(userDetails.getId(), roomId));
    }

    @GetMapping
    public ResponseEntity<List<Room>> getUserRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(roomAdminService.getUserRooms(userDetails.getId()));
    }

    @PostMapping(Endpoints.ROOM.ADMIN.ACTIVATE)
    public ResponseEntity<Void> toggleActive(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestBody RoomVO room) {
        roomAdminService.toggleRoomActivation(userDetails.getId(), room.getRoomId());
        return ResponseEntity.ok().build();
    }

    @PostMapping(Endpoints.ROOM.ADMIN.TOGGLE_PLAY)
    public ResponseEntity<Boolean> togglePlayPause(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return roomAdminService.togglePlayPause(userDetails.getId());
    }

}
