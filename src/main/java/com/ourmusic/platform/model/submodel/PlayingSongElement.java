package com.ourmusic.platform.model.submodel;

import com.wrapper.spotify.model_objects.specification.Track;
import lombok.Data;

@Data
public class PlayingSongElement {

    private TrackObject track;

    private Integer progressMs = 0;

    private Integer skipVotes = 0;

}
