package com.ourmusic.platform.model;

import com.ourmusic.platform.model.spotify.Track;
import lombok.Data;

import java.util.List;

@Data
public class Queue {

    public List<Track> songs;

}
