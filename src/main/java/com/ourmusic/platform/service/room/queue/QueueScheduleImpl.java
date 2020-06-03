package com.ourmusic.platform.service.room.queue;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.model.submodel.PlayingSongElement;
import com.ourmusic.platform.model.submodel.QueueElement;
import com.ourmusic.platform.model.submodel.TrackObject;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.service.spotify.SpotifyPlayerService;
import com.ourmusic.platform.websocket.CurrentSongSocket;
import com.ourmusic.platform.websocket.QueueSocket;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import com.wrapper.spotify.model_objects.specification.Track;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ourmusic.platform.util.converter.trackToTrackObjectUtil.trackToTrackObject;

public class QueueScheduleImpl implements QueueSchedule {

    private ThreadPoolTaskScheduler taskScheduler;
    private RoomRepository roomRepository;
    private CurrentSongSocket currentSongSocket;
    private QueueSocket queueSocket;


    private ScheduledFuture<?> scheduledFuture;
    private final Room room;

    private SpotifyPlayerService spotifyPlayerService;


    public QueueScheduleImpl(RoomRepository roomRepository, ThreadPoolTaskScheduler taskScheduler, Room room,
                             SpotifyPlayerService spotifyPlayerService, CurrentSongSocket currentSongSocket,
                             QueueSocket queueSocket) {
        this.roomRepository = roomRepository;
        this.taskScheduler = taskScheduler;
        this.room = room;
        this.spotifyPlayerService = spotifyPlayerService;
        this.currentSongSocket = currentSongSocket;
        this.queueSocket = queueSocket;
    }

    @Override
    public boolean isPlaying() {
        return !(this.scheduledFuture == null || this.scheduledFuture.isCancelled() || this.scheduledFuture.isDone());
    }

    @Override
    public void playRoom() {
        this.scheduledFuture = taskScheduler.scheduleWithFixedDelay(
                new QueueTask(room, this.spotifyPlayerService, this.roomRepository, currentSongSocket, queueSocket),
                4000);
    }

    @Override
    public void pauseRoom() {
        this.scheduledFuture.cancel(true);
    }

    private static class QueueTask implements Runnable{
        private Room room;
        private final SpotifyPlayerService spotifyPlayerService;
        private final RoomRepository roomRepository;
        private final CurrentSongSocket currentSongSocket;
        private final QueueSocket queueSocket;

        private final AtomicInteger previousTime = new AtomicInteger(0);

        public QueueTask(Room room, SpotifyPlayerService spotifyPlayerService, RoomRepository roomRepository,
                         CurrentSongSocket currentSongSocket, QueueSocket queueSocket){
            this.room = room;
            this.spotifyPlayerService = spotifyPlayerService;
            this.roomRepository = roomRepository;
            this.currentSongSocket = currentSongSocket;
            this.queueSocket = queueSocket;

            System.out.println(new Date()+" Runnable Task with "+ room.getId() +" on thread "+ Thread.currentThread().getName());

        }

        @Override
        public void run() {

            CurrentlyPlayingContext usersCurrentPlayback = spotifyPlayerService.getUsersCurrentPlayback(room.getHostId());

            //Current track is empty or soon ending, queue up next song and lock
            if ( usersCurrentPlayback == null || (usersCurrentPlayback.getProgress_ms() > ((Track)usersCurrentPlayback.getItem()).getDurationMs() * 0.95
                    && noCurrentLock())) {
                addTrackToPlayback();
            }

            //New song playing, update current song
            if (usersCurrentPlayback != null && previousTime.get() > usersCurrentPlayback.getProgress_ms()) {
                updateCurrentSongForRoom();
            }

            //Current track differs from playback, update current song
            if ( room.getPlayingSong() == null
                    || !((Track)usersCurrentPlayback.getItem()).getUri().equals(room.getPlayingSong().getTrack().getUri())){
                setSongWhenDifferent((Track)usersCurrentPlayback.getItem());
            }

            //Check song votes, skip song
            if (room.getPlayingSong().getSkipVotes() > (room.getUsersEstimate() * 0.5)) {
                addTrackToPlayback();
                skipCurrentSong();
            }

            this.room = roomRepository.findById(room.getId()).orElse(this.room);
            this.room.getPlayingSong().setProgressMs(usersCurrentPlayback.getProgress_ms());

            currentSongSocket.sendMessage(room.getCode(), room.getPlayingSong());

            previousTime.set(usersCurrentPlayback.getProgress_ms());

        }

        private void addTrackToPlayback() {
            Optional<String> trackUriOpt = getHighestVotedOrEarliestTrackAndLock();
            trackUriOpt.ifPresent(trackUri -> spotifyPlayerService.addTrackToPlayback(room.getHostId(), trackUri));
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
            this.roomRepository.save(room);
            queueSocket.sendMessage(room);
        }

        private void setSongWhenDifferent(Track track) {
            TrackObject trackObject = trackToTrackObject(track);
            PlayingSongElement playingSongElement = new PlayingSongElement();
            playingSongElement.setTrack(trackObject);
            room.setPlayingSong(playingSongElement);
            roomRepository.save(room);
        }

        private void updateCurrentSongForRoom() {
            Optional<QueueElement> queueElementOpt = getLockedElementOrBackup();

            if (queueElementOpt.isPresent()) {
                QueueElement element = queueElementOpt.get();
                PlayingSongElement playingSongElement = new PlayingSongElement();
                playingSongElement.setTrack(element.getSong());
                room.setPlayingSong(playingSongElement);

                room.getQueue().remove(element);
                queueSocket.sendMessage(room);
            } else {
                room.setPlayingSong(null);
            }
            roomRepository.save(room);
        }

        private Optional<QueueElement> getLockedElementOrBackup() {

            Optional<QueueElement> lockedElement = room.getQueue().stream()
                    .filter(QueueElement::isVoteLocked)
                    .findAny();

            if (lockedElement.isPresent()) {
                return lockedElement;
            }
            return getQueueElement();
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

        private boolean noCurrentLock() {
            return room.getQueue().stream().noneMatch(QueueElement::isVoteLocked);
        }

        private void skipCurrentSong() {
            spotifyPlayerService.skipPlayingTrack(room.getHostId());
        }
    }

}
