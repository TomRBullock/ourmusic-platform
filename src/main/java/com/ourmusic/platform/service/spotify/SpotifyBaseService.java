package com.ourmusic.platform.service.spotify;

import com.wrapper.spotify.model_objects.specification.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ourmusic.spotify.client.SpotifyClient;

public abstract class SpotifyBaseService {

    @Autowired SpotifyClient spotifyClient;

    public User getSpotifyUserDetailsWithAccessCode(String accessCode) {
        return spotifyClient.currentUserDetails().getCurrentUsersProfile_Sync(accessCode);
    }

}
