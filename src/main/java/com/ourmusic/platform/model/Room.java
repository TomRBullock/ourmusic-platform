package com.ourmusic.platform.model;

import com.ourmusic.platform.model.submodel.PlayingSongElement;
import com.ourmusic.platform.model.submodel.QueueElement;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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

    private PlayingSongElement playingSong;
    private List<QueueElement> queue = new ArrayList<>();

    private boolean play = false;

}
