package com.ourmusic.platform.controller.spotify;

import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.service.spotify.SpotifySearchService;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.model_objects.AbstractModelObject;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.SPOTIFY.SEARCH.ROOT)
public class SpotifySearchResource {

    private final SpotifySearchService spotifySearchService;

    @GetMapping
    public ResponseEntity<List<AbstractModelObject>> searchForTrack(@RequestParam String searchTerm,
                                                                    @RequestParam String roomCode,
                                                                    @RequestParam ModelObjectType type) {
        return spotifySearchService.search(searchTerm, roomCode, type);
    }

}
