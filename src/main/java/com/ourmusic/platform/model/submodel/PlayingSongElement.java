package com.ourmusic.platform.model.submodel;

import com.wrapper.spotify.model_objects.specification.Track;
import lombok.Data;

@Data
public class PlayingSongElement {

    private TrackObject track;
    private Integer skipVotes = 0;

}
