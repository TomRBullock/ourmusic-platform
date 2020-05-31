package com.ourmusic.platform.service.room;

import com.ourmusic.platform.vo.request.room.RoomSetupVO;
import org.springframework.http.ResponseEntity;

public interface RoomSetupService {

    void createNewRoom(String userId, RoomSetupVO setupVO);

    boolean deleteRoom(String userId, String roomCode);

    void activateRoom(String userId, String roomCode);

    ResponseEntity<Boolean> togglePlayPause(String userId);

}
