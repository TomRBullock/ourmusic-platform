package com.ourmusic.platform.service.spotify;

import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;

public interface SpotifyPlayerService {

    boolean togglePlayPause(String hostId, boolean playState);

    CurrentlyPlayingContext getUsersCurrentPlayback(String hostId);

    void addTrackToPlayback(String hostId, String trackUri);

    void skipPlayingTrack(String hostId);

}
