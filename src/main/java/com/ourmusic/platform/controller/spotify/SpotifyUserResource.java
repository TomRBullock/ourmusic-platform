package com.ourmusic.platform.controller.spotify;

import com.ourmusic.platform.app.config.security.UserDetailsImpl;
import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.service.spotify.SpotifyUserService;
import com.wrapper.spotify.model_objects.specification.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.SPOTIFY.USER.ROOT)
public class SpotifyUserResource {

    private final SpotifyUserService spotifyUserService;

    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping
    public ResponseEntity<User> getUserDetails(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.of(spotifyUserService.getSpotifyUserDetails(userDetails.getId()));
    }

}
