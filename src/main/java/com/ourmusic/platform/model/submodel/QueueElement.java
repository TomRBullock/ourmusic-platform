package com.ourmusic.platform.model.submodel;

import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class QueueElement {

    private TrackSimplified song;
    private Integer votes;
    private Instant timeAdded;
    private boolean voteLocked = false;

}
