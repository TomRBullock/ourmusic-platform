package com.ourmusic.platform.model.submodel;

import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QueueElement {

    public TrackSimplified song;
    public Integer votes;

}
