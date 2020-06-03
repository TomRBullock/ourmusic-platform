package com.ourmusic.platform.controller.spotify;

import com.ourmusic.platform.app.config.security.UserDetailsImpl;
import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.service.spotify.SpotifyAuthorizationService;
import com.ourmusic.platform.vo.SpotifyCodeRequestBodyVO;
import com.wrapper.spotify.model_objects.specification.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ourmusic.spotify.client.vo.AuthURIResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.SPOTIFY.AUTHORIZATION.ROOT)
public class SpotifyAuthorizationResource {

    private final SpotifyAuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<AuthURIResponse> authUser() {
        return authorizationService.authUser();
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @PostMapping(Endpoints.SPOTIFY.AUTHORIZATION.CODE)
    public ResponseEntity<User> getAccessTokenFromCode(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @RequestBody SpotifyCodeRequestBodyVO codeBody) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        User spotifyUserDetails = authorizationService.accessTokenFromCode(userDetails.getId(), codeBody.getCode());
        if (spotifyUserDetails != null) {
            return ResponseEntity.ok(spotifyUserDetails);
        }

        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping(Endpoints.SPOTIFY.AUTHORIZATION.IS_CONNECTED)
    public ResponseEntity<Boolean> checkSpotifyConnected(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(authorizationService.isSpotifyAuthorised(userDetails.getId()));
    }
}
