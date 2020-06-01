package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
