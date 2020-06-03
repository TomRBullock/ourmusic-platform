package com.ourmusic.platform.service.spotify;

import com.ourmusic.platform.repository.UserSpotifyCredentialsRepository;
import com.wrapper.spotify.model_objects.specification.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ourmusic.spotify.client.SpotifyClient;

import java.util.Optional;

@Service
public class SpotifyUserServiceImpl extends SpotifyBaseService implements SpotifyUserService {

    @Autowired private SpotifyAuthorizationService spotifyAuthorizationService;

    @Override
    public Optional<User> getSpotifyUserDetails(String userId) {
        Optional<String> validAccessTokenOpt = spotifyAuthorizationService.getValidAccessToken(userId);

        return validAccessTokenOpt
                .map(this::getSpotifyUserDetailsWithAccessCode);
    }
}
