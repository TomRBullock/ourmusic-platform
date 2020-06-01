package com.ourmusic.platform.controller.room;

import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoints.ROOM.ROOT + Endpoints.UTIL.ID_VAR)
@RequiredArgsConstructor
public class RoomResource {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<Room> getRoom(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomid) {
        return ResponseEntity.of(roomService.getRoom(roomid));
    }

    @GetMapping(Endpoints.ROOM.VALIDATE)
    public ResponseEntity<Boolean> checkRoomValidity(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomId) {
        return ResponseEntity.ok(roomService.checkRoomIsValid(roomId));
    }
}
