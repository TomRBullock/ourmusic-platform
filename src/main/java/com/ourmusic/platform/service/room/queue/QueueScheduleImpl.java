package com.ourmusic.platform.service.room.queue;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.service.spotify.SpotifyPlayerService;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

//@Service
//@RequiredArgsConstructor
public class QueueScheduleImpl implements QueueSchedule {

    private ThreadPoolTaskScheduler taskScheduler;
    private RoomRepository roomRepository;

    private ScheduledFuture<?> scheduledFuture;
    private final Room room;

    private SpotifyPlayerService spotifyPlayerService;


    public QueueScheduleImpl(RoomRepository roomRepository, ThreadPoolTaskScheduler taskScheduler, Room room,
                             SpotifyPlayerService spotifyPlayerService) {
        this.roomRepository = roomRepository;
        this.taskScheduler = taskScheduler;
        this.room = room;
        this.spotifyPlayerService = spotifyPlayerService;
    }

    @Override
    public boolean isPlaying() {
        return !(this.scheduledFuture == null || this.scheduledFuture.isCancelled() || this.scheduledFuture.isDone());
    }

    @Override
    public void playRoom() {
        this.scheduledFuture = taskScheduler.scheduleWithFixedDelay(new QueueTask(room.getHostId(), this.spotifyPlayerService), 5000);
    }

    @Override
    public void pauseRoom() {
        this.scheduledFuture.cancel(true);
    }

    class QueueTask implements Runnable{
        private String hostId;
        private SpotifyPlayerService spotifyPlayerService;

        public QueueTask(String hostId, SpotifyPlayerService spotifyPlayerService){
            this.hostId = hostId;
            this.spotifyPlayerService = spotifyPlayerService;
        }

        @Override
        public void run() {
            System.out.println(new Date()+" Runnable Task with "+ hostId
                    +" on thread "+ Thread.currentThread().getName());


            CurrentlyPlayingContext usersCurrentPlayback = spotifyPlayerService.getUsersCurrentPlayback(hostId);
            System.out.println(usersCurrentPlayback.getProgress_ms());

        }
    }

}
