package com.ourmusic.platform.model;

import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.Data;

import java.util.List;

@Data
public class QueueElement {

    public TrackSimplified song;
    public Integer votes;

}
