package com.ourmusic.platform.service.spotify;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ourmusic.spotify.client.SpotifyClient;
import ourmusic.spotify.client.vo.AuthURIResponse;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final SpotifyClient spotifyClient;

    @Override
    public ResponseEntity<AuthURIResponse> authUser() {
        AuthURIResponse uri = spotifyClient.authCodeUri().authorizationCodeUri_Sync();
        if (uri.getUri() == null) {
            return ResponseEntity.noContent().build();
        }

        //update user
        return ResponseEntity.ok(uri);

    }
}
