package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.vo.response.room.RoomValidityCheckVO;

import java.util.Optional;

public interface RoomService {

    Optional<Room> getRoom(String roomRef);

    RoomValidityCheckVO checkRoomIsValid(String roomRef);

}
