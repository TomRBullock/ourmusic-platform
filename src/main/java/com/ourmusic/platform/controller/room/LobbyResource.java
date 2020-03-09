package com.ourmusic.platform.controller.room;

import com.ourmusic.platform.controller.Endpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoints.ROOM.ROOT)
public class LobbyResource {

    @GetMapping
    public ResponseEntity<String> findAndReturnRoom() {
        return ResponseEntity.ok("yep");
    }
}
