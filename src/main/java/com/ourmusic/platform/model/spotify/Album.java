package com.ourmusic.platform.model.spotify;

import com.ourmusic.platform.util.enums.DatePrecisionEnum;
import lombok.Data;


@Data
public class Album {

    private String spotifyId;

    private String externalUrl;
    private String href;
    private String uri;

    private String name;
//    private DatePrecisionEnum releaseDatePrecision;
//    private

}
