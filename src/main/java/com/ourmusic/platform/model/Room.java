package com.ourmusic.platform.model;

import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Room extends BaseDocument {

    private TrackSimplified playingSong;
    private String hostId;
    private boolean active = false;
    private boolean passwordProtected = false;

    private String roomName;
    private String description;

    //6 character String
    private String code;
    private String password;

}
