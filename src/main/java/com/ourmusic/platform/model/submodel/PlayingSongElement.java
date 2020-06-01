package com.ourmusic.platform.model.submodel;

import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.Data;

@Data
public class PlayingSongElement {

    private TrackSimplified track;

    private Integer skipVotes = 0;

}
