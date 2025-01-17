package com.ourmusic.platform.service.spotify;

import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotifyPlayerServiceImpl extends SpotifyBaseService implements SpotifyPlayerService {

    @Autowired private SpotifyAuthorizationService spotifyAuthorizationService;

    @Override
    public boolean togglePlayPause(String hostId, boolean playState) {
        boolean newPlayState = !playState;

        Optional<String> validAccessTokenOpt = spotifyAuthorizationService.getValidAccessToken(hostId);

        if (!validAccessTokenOpt.isPresent()) {
            return false;
        }

        String accessToken = validAccessTokenOpt.get();
        if (newPlayState) {
            spotifyClient.togglePlay().startResumeUsersPlayback_Sync(accessToken);
        } else {
            spotifyClient.togglePlay().pauseUsersPlayback_Sync(accessToken);
        }

        return true;
    }

    @Override
    public CurrentlyPlayingContext getUsersCurrentPlayback(String hostId) {
        Optional<String> validAccessTokenOpt = spotifyAuthorizationService.getValidAccessToken(hostId);
        return validAccessTokenOpt
                .map(accessToken -> spotifyClient.currentPlaybackInfo().getInformationAboutUsersCurrentPlayback_Sync(accessToken))
                .orElse(null);
    }

    @Override
    public void addTrackToPlayback(String hostId, String trackUri) {
        Optional<String> validAccessTokenOpt = spotifyAuthorizationService.getValidAccessToken(hostId);

        validAccessTokenOpt.ifPresent(accessToken -> {
            spotifyClient.addTrackToQueue().addItemToUsersPlaybackQueue_Sync(accessToken, trackUri);
        });
    }

    @Override
    public void skipPlayingTrack(String hostId) {
        Optional<String> validAccessTokenOpt = spotifyAuthorizationService.getValidAccessToken(hostId);

        validAccessTokenOpt.ifPresent(accessToken -> {
            spotifyClient.skipTrack().skipUsersPlaybackToNextTrack_Sync(accessToken);
        });
    }
}
