package com.ourmusic.platform.model;

import com.ourmusic.platform.model.spotify.Track;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Room extends BaseDocument {

//    public Queue queue;

    public Track playingSong;

    public String hostRef;

}
