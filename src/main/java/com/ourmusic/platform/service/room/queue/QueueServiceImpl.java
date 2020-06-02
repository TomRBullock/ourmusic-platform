package com.ourmusic.platform.service.room.queue;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.service.spotify.SpotifyPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {


    private final ThreadPoolTaskScheduler taskScheduler;
    private final RoomRepository roomRepository;
    private final SpotifyPlayerService spotifyPlayerService;
    private final CurrentSongService currentSongService;

    private final Map<String, QueueSchedule> queueScheduleMap = new HashMap<>();

    @Override
    public void startQueue(Room room) {
        QueueSchedule queueSchedule = getQueueSchedule(room);
        if (!queueSchedule.isPlaying()) {
            queueSchedule.playRoom();
        }
    }

    @Override
    public void stopQueue(Room room) {
        QueueSchedule queueSchedule = getQueueSchedule(room);
        if (queueSchedule.isPlaying()) {
            queueSchedule.pauseRoom();
        }
    }

    private QueueSchedule getQueueSchedule(Room room) {
        if (queueScheduleMap.containsKey(room.getId())) {
            return queueScheduleMap.get(room.getId());

        } else {
            QueueScheduleImpl queueSchedule = new QueueScheduleImpl(roomRepository, taskScheduler, room, spotifyPlayerService, currentSongService);
            queueScheduleMap.put(room.getId(), queueSchedule);
            return queueSchedule;
        }
    }

}
