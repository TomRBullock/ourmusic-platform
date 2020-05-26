package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.vo.response.room.RoomValidityCheckVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Optional<Room> getRoom(String roomRef) {
        return roomRepository.findById(roomRef);
    }

    @Override
    public RoomValidityCheckVO checkRoomIsValid(String roomRef) {
        RoomValidityCheckVO roomValidityCheckVO = new RoomValidityCheckVO();

        Optional<Room> roomOpt = roomRepository.findById(roomRef);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            if (room.isActive())
                roomValidityCheckVO.setValid(true);
        }
        return roomValidityCheckVO;
    }
}
