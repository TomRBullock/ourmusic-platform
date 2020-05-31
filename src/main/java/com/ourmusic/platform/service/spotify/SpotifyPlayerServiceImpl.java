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

        CurrentlyPlayingContext playingContext = spotifyClient.currentPlaybackInfo().getInformationAboutUsersCurrentPlayback_Sync(accessToken);

        if (playingContext != null) {
            return playingContext.getIs_playing() == newPlayState;
        }

        return false;
    }
}
