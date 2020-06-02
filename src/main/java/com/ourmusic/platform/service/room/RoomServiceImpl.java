package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.submodel.PlayingSongElement;
import com.ourmusic.platform.model.submodel.QueueElement;
import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.model.submodel.TrackObject;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.websocket.QueueSocket;
import com.wrapper.spotify.model_objects.specification.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ourmusic.platform.util.converter.trackToTrackObjectUtil.trackToTrackObject;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final QueueSocket queueSocket;

    @Override
    public Optional<Room> getRoom(String roomCode) {
        return roomRepository.findByCodeAndActiveIsTrue(roomCode);
    }

    @Override
    public boolean checkRoomIsValid(String roomCode) {
        Optional<Room> roomOpt = roomRepository.findByCodeAndActiveIsTrue(roomCode);
        if (roomOpt.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addSongToQueue(String roomCode, Track track) {

        TrackObject trackObject = trackToTrackObject(track);

        Optional<Room> roomOpt = roomRepository.findByCode(roomCode);

        if (!roomOpt.isPresent()) {
            return false;
        }

        Room room = roomOpt.get();
        QueueElement queueElement = new QueueElement();
        queueElement.setSong(trackObject);
        queueElement.setTimeAdded(Instant.now());
        queueElement.setVotes(0);
        queueElement.setVoteLocked(false);

        room.getQueue().add(queueElement);
        roomRepository.save(room);

        queueSocket.sendMessage(room);

        return true;
    }

    @Override
    public void updateUserEstimate(String roomCode, boolean joined) {
        Optional<Room> roomOpt = roomRepository.findByCode(roomCode);

        roomOpt.ifPresent(room -> {
            if (joined) {
                room.setUsersEstimate(room.getUsersEstimate() + 1);
            } else {
                room.setUsersEstimate(room.getUsersEstimate() - 1);
            }
            roomRepository.save(room);
        });
    }
}
