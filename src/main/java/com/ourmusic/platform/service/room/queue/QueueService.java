package com.ourmusic.platform.service.room.queue;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.model.submodel.QueueElement;

public interface QueueService {

    void startQueue(Room room);

    void stopQueue(Room room);

    void addVote(String roomCode, QueueElement track);

    void removeVote(String roomCode, QueueElement track);

    void addSkipVote(String roomCode);
}
