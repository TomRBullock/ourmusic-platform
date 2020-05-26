package com.ourmusic.platform.controller.spotify;

import com.ourmusic.platform.controller.Endpoints;
import com.ourmusic.platform.service.spotify.AuthorizationService;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ourmusic.spotify.client.vo.AuthURIResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.SPOTIFY.AUTHORIZATION.ROOT)
public class AuthorizationResource {

    private final AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<AuthURIResponse> authUser() {
        return authorizationService.authUser();
    }

//    @GetMapping(Endpoints.SPOTIFY.AUTHORIZATION.REDIRECT)
//    public void spotifyAuthRedirect(AuthorizationCodeCredentials authCredentials) {
//        String accessToken = authCredentials.getAccessToken();
//    }


}
