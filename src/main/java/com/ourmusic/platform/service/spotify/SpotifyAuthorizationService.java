package com.ourmusic.platform.service.spotify;

import com.wrapper.spotify.model_objects.specification.User;
import org.springframework.http.ResponseEntity;
import ourmusic.spotify.client.vo.AuthURIResponse;

import java.util.Optional;

public interface SpotifyAuthorizationService {

    ResponseEntity<AuthURIResponse> authUser();

    User accessTokenFromCode(String userId, String code);

    boolean isSpotifyAuthorised(String userId);

    Optional<String> getValidAccessToken(String userId);

}
