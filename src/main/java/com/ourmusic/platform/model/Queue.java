package com.ourmusic.platform.model;

import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.Data;

import java.util.List;

@Data
public class Queue {

    public List<TrackSimplified> songs;

}
