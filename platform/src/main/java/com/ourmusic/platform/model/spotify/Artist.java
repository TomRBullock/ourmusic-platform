package com.ourmusic.platform.model.spotify;

import lombok.Data;

@Data
public class Artist {

    private String spotifyId;

    private String externalUrl;
    private String href;
    private String uri;

    private String name;

}
