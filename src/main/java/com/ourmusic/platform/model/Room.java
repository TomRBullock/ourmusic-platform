package com.ourmusic.platform.model;

import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Room extends BaseDocument {

    //6 character String
    private @Indexed(unique = true) String code;

    private @Indexed String hostId;
    private boolean active = false;
    private boolean passwordProtected = false;

    private String roomName;
    private String description;

    private String password;

    private TrackSimplified playingSong;
    private QueueElement queue;

    private boolean play = false;

}
