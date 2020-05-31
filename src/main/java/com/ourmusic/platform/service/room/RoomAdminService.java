package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.vo.request.room.RoomSetupVO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomAdminService {

    void createNewRoom(String userId, RoomSetupVO setupVO);

    boolean deleteRoom(String userId, String roomId);

    void toggleRoomActivation(String userId, String roomId);

    ResponseEntity<Boolean> togglePlayPause(String userId);

    List<Room> getUserRooms(String userId);

}
