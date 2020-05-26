package com.ourmusic.platform.vo.request.room;

import lombok.Data;

@Data
public class RoomSetupVO {
    private String hostRef;
    private String roomName;
    private String description;
    private String password;
}
