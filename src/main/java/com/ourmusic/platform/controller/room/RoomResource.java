package com.ourmusic.platform.controller.room;

import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.service.room.RoomService;
import com.ourmusic.platform.vo.response.room.RoomValidityCheckVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoints.ROOM.ROOT)
@RequiredArgsConstructor
public class RoomResource {

    private final RoomService roomService;

    @GetMapping(Endpoints.UTIL.ID_VAR)
    public ResponseEntity<Room> getRoom(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomid) {
        return ResponseEntity.of(roomService.getRoom(roomid));
    }

    @GetMapping(Endpoints.UTIL.ID_VAR + Endpoints.ROOM.VALIDATE)
    public ResponseEntity<RoomValidityCheckVO> checkRoomValidity(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomId) {
        return ResponseEntity.ok(roomService.checkRoomIsValid(roomId));
    }
}
