package com.ourmusic.platform.controller.room;

import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.model.submodel.PlayingSongElement;
import com.ourmusic.platform.model.submodel.QueueElement;
import com.ourmusic.platform.service.room.RoomService;
import com.ourmusic.platform.vo.request.room.AddSongToQueueVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.ROOM.ROOT + Endpoints.UTIL.ID_VAR)
@RequiredArgsConstructor
public class RoomResource {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<Room> getRoom(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomCode) {
        return ResponseEntity.of(roomService.getRoom(roomCode));
    }

    @GetMapping(Endpoints.ROOM.VALIDATE)
    public ResponseEntity<Boolean> checkRoomValidity(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomCode) {
        return ResponseEntity.ok(roomService.checkRoomIsValid(roomCode));
    }

    @PostMapping(Endpoints.ROOM.QUEUE)
    public ResponseEntity<Boolean> addTrackToQueue(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomCode,
                                                   @RequestBody AddSongToQueueVO addSongToQueueVO) {

        roomService.addSongToQueue(roomCode, addSongToQueueVO.getTrack());
        return ResponseEntity.ok(true);
    }

    @GetMapping(Endpoints.ROOM.QUEUE)
    public ResponseEntity<List<QueueElement>> getRoomQueue(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomCode) {
        return ResponseEntity.ok(roomService.getRoomQueue(roomCode));
    }

    @GetMapping(Endpoints.ROOM.SONG)
    public ResponseEntity<PlayingSongElement> getPlayingSong(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomCode) {
        return ResponseEntity.of(roomService.getPlayingSong(roomCode));
    }

}
