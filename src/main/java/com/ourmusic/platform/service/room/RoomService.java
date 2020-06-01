package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.Room;

import java.util.Optional;

public interface RoomService {

    Optional<Room> getRoom(String roomRef);

    boolean checkRoomIsValid(String roomRef);

}
