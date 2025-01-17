package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.model.submodel.PlayingSongElement;
import com.ourmusic.platform.model.submodel.QueueElement;
import com.wrapper.spotify.model_objects.specification.Track;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Optional<Room> getRoom(String roomCode);

    boolean checkRoomIsValid(String roomCode);

    boolean addSongToQueue(String roomCode, Track track);

    void updateUserEstimate(String roomCode, boolean joined);

}
