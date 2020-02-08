package com.ourmusic.platform.model.spotify;

import lombok.Data;

import java.util.List;

@Data
public class Track {

    private String spotifyId;

    private String externalUrl;
    private String href;
    private String uri;

    private String name;
    private int msDuration;
    private boolean explicit;
    private boolean isLocal;
    private boolean isPlayable;

    private Album album;
    private List<Artist> artists;

}
