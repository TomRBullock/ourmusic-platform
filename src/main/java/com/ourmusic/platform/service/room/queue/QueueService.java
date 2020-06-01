package com.ourmusic.platform.service.room.queue;

import com.ourmusic.platform.model.Room;

public interface QueueService {

    void startQueue(Room room);

    void stopQueue(Room room);

}
