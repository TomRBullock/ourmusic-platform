package com.ourmusic.platform.controller.room;

import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.model.submodel.QueueElement;
import com.ourmusic.platform.service.room.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoints.ROOM.VOTES.ROOT)
@RequiredArgsConstructor
public class QueueVoteController {

    private final QueueService queueService;

    @PostMapping(Endpoints.ROOM.VOTES.ADD)
    public ResponseEntity<Void> addVoteToSong(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomCode,
                              @RequestBody QueueElement track) {
        queueService.addVote(roomCode, track);
        return ResponseEntity.ok().build();
    }

    @PostMapping(Endpoints.ROOM.VOTES.REMOVE)
    public ResponseEntity<Void> removeVoteFromSong(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomCode,
                            @RequestBody QueueElement track) {
        queueService.removeVote(roomCode, track);
        return ResponseEntity.ok().build();
    }

    @PostMapping(Endpoints.ROOM.VOTES.SKIP)
    public ResponseEntity<Void> voteSkipForCurrentSong(@PathVariable(Endpoints.UTIL.ID_PARAM) String roomCode) {
        queueService.addSkipVote(roomCode);
        return ResponseEntity.ok().build();
    }

}
