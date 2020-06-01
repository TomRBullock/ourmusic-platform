package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.submodel.PlayingSongElement;
import com.ourmusic.platform.model.submodel.QueueElement;
import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.repository.RoomRepository;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

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
    public boolean addSongToQueue(String roomCode, TrackSimplified track) {

        Optional<Room> roomOpt = roomRepository.findByCode(roomCode);

        if (!roomOpt.isPresent()) {
            return false;
        }

        Room room = roomOpt.get();
        room.getQueue().add(new QueueElement(track, 0));
        roomRepository.save(room);

        return true;
    }

    @Override
    public List<QueueElement> getRoomQueue(String roomCode) {

        Optional<Room> roomOpt = roomRepository.findByCode(roomCode);

        if (roomOpt.isPresent()) {
            return roomOpt.get().getQueue();
        }

        return new ArrayList<>();
    }

    @Override
    public Optional<PlayingSongElement> getPlayingSong(String roomCode) {
        Optional<Room> roomOpt = roomRepository.findByCode(roomCode);
        return roomOpt.map(Room::getPlayingSong);
    }
}
