package com.ourmusic.platform.model.submodel;

import lombok.Data;

import java.time.Instant;

@Data
public class QueueElement {

    private TrackObject song;
    private Integer votes;
    private Instant timeAdded;
    private boolean voteLocked = false;

}
