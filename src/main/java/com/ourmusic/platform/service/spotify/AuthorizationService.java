package com.ourmusic.platform.service.spotify;

import org.springframework.http.ResponseEntity;
import ourmusic.spotify.client.vo.AuthURIResponse;

public interface AuthorizationService {

    ResponseEntity<AuthURIResponse> authUser();

}
