package com.ourmusic.platform.service.room;

import com.ourmusic.platform.vo.request.room.RoomSetupVO;

public interface RoomSetupService {

    void createNewRoom(String userId, RoomSetupVO setupVO);

}
