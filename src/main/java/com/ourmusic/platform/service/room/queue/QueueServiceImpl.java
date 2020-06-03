package com.ourmusic.platform.service.room.queue;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.model.submodel.QueueElement;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.service.spotify.SpotifyPlayerService;
import com.ourmusic.platform.websocket.CurrentSongSocket;
import com.ourmusic.platform.websocket.QueueSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {


    private final ThreadPoolTaskScheduler taskScheduler;
    private final RoomRepository roomRepository;
    private final SpotifyPlayerService spotifyPlayerService;
    private final CurrentSongSocket currentSongSocket;
    private final QueueSocket queueSocket;

    private final Map<String, QueueSchedule> queueScheduleMap = new HashMap<>();

    @PostConstruct
    public void startPlayingQueues() {
        List<Room> playingRooms = roomRepository.findAllByPlayTrue();
        playingRooms.forEach(this::startQueue);
    }

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
            QueueScheduleImpl queueSchedule = new QueueScheduleImpl(
                    roomRepository, taskScheduler, room, spotifyPlayerService, currentSongSocket, queueSocket);
            queueScheduleMap.put(room.getId(), queueSchedule);
            return queueSchedule;
        }
    }


    @Override
    public void addVote(String roomCode, QueueElement track) {

        Optional<Room> roomOpt = getRoom(roomCode);

        Optional<QueueElement> elementOpt = roomOpt.flatMap(room -> getQueueElement(room, track));

        elementOpt.ifPresent(element -> {
            element.setVotes(element.getVotes() + 1);
            roomRepository.save(roomOpt.get());
            queueSocket.sendMessage(roomOpt.get());
        });
    }

    @Override
    public void removeVote(String roomCode, QueueElement track) {
        Optional<Room> roomOpt = getRoom(roomCode);

        Optional<QueueElement> elementOpt = roomOpt.flatMap(room -> getQueueElement(room, track));

        elementOpt.ifPresent(element -> {
            element.setVotes(element.getVotes() - 1);
            roomRepository.save(roomOpt.get());
            queueSocket.sendMessage(roomOpt.get());
        });
    }

    @Override
    public void addSkipVote(String roomCode) {
        getRoom(roomCode).ifPresent(room -> {
            room.getPlayingSong().setSkipVotes(room.getPlayingSong().getSkipVotes() + 1);
            roomRepository.save(room);
            currentSongSocket.sendMessage(roomCode, room.getPlayingSong());
        });
    }

    private Optional<Room> getRoom(String roomCode) {
        return roomRepository.findByCode(roomCode);
    }

    private Optional<QueueElement> getQueueElement(Room room, QueueElement track) {
        return room.getQueue().stream()
                .filter(queueElement ->
                        queueElement.getTimeAdded().equals(track.getTimeAdded())
                                && queueElement.getSong().getUri().equals(track.getSong().getUri()))
                .findAny();
    }
}
