package com.ourmusic.platform.model.submodel;

import com.wrapper.spotify.model_objects.specification.Track;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class QueueElement {

    private TrackObject song;
    private Integer votes;
    private Instant timeAdded;
    private boolean voteLocked = false;

}
