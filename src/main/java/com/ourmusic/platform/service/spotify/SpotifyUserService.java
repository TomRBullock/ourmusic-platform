package com.ourmusic.platform.service.spotify;

import com.wrapper.spotify.model_objects.specification.User;

import java.util.Optional;

public interface SpotifyUserService {

    Optional<User> getSpotifyUserDetails(String userId);

    com.wrapper.spotify.model_objects.specification.User getSpotifyUserDetailsWithAccessCode(String accessCode);


}
