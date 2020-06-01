package com.ourmusic.platform.service.room.queue;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.model.submodel.PlayingSongElement;
import com.ourmusic.platform.model.submodel.QueueElement;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.service.spotify.SpotifyPlayerService;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;

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
        this.scheduledFuture = taskScheduler.scheduleWithFixedDelay(new QueueTask(room, this.spotifyPlayerService, this.roomRepository), 4000);
    }

    @Override
    public void pauseRoom() {
        this.scheduledFuture.cancel(true);
    }

    private static class QueueTask implements Runnable{
        private final Room room;
        private final SpotifyPlayerService spotifyPlayerService;
        private final RoomRepository roomRepository;

        private final AtomicInteger previousTime = new AtomicInteger(0);

        public QueueTask(Room room, SpotifyPlayerService spotifyPlayerService, RoomRepository roomRepository){
            this.room = room;
            this.spotifyPlayerService = spotifyPlayerService;
            this.roomRepository = roomRepository;
        }

        @Override
        public void run() {
            System.out.println(new Date()+" Runnable Task with "+ room.getId() +" on thread "+ Thread.currentThread().getName());

            CurrentlyPlayingContext usersCurrentPlayback = spotifyPlayerService.getUsersCurrentPlayback(room.getHostId());

            //Current track soon ending, queue up next song and lock
            if (usersCurrentPlayback.getProgress_ms() > usersCurrentPlayback.getItem().getDurationMs() * 0.9) {
                Optional<String> trackUriOpt = getHighestVotedOrEarliestTrackAndLock();
                trackUriOpt.ifPresent(trackUri -> spotifyPlayerService.addTrackToPlayback(room.getHostId(), trackUri));
            }

            //New song playing, update current song
            if (previousTime.get() > usersCurrentPlayback.getProgress_ms()) {
                updateCurrentSongForRoom();
            }

            previousTime.set(usersCurrentPlayback.getProgress_ms());

        }

        private Optional<String> getHighestVotedOrEarliestTrackAndLock() {
            Optional<QueueElement> queueElementOpt = getQueueElement();

            if (!queueElementOpt.isPresent()) {
                return Optional.empty();
            }

            voteLockQueueElement(queueElementOpt.get());

            return Optional.of(queueElementOpt.get().getSong().getUri());
        }

        private void voteLockQueueElement(QueueElement queueElement) {
            queueElement.setVoteLocked(true);
//            getQueueElement(queueElement).ifPresent(element -> element.setVoteLocked(true));
            this.roomRepository.save(room);
        }

        private void updateCurrentSongForRoom() {
            getQueueElement().ifPresent(element -> {

                PlayingSongElement playingSongElement = new PlayingSongElement();
                playingSongElement.setTrack(element.getSong());
                room.setPlayingSong(playingSongElement);

                room.getQueue().remove(element);

                roomRepository.save(room);

            });
        }

        private Optional<QueueElement> getQueueElement() {
            return Optional.ofNullable(room.getQueue().stream()
                    .max(Comparator.comparing(QueueElement::getVotes))
                    .orElse(
                            room.getQueue().stream()
                                    .max(Comparator.comparing(QueueElement::getTimeAdded))
                                    .orElse(null)
                    ));
        }

//        private Optional<QueueElement> getQueueElement(QueueElement queueElement) {
//            return room.getQueue().stream()
//                    .filter(element -> element.getTimeAdded().equals(queueElement.getTimeAdded())
//                            && element.getSong().getId().equals(queueElement.getSong().getId()))
//                    .findAny();
//        }
    }

}
