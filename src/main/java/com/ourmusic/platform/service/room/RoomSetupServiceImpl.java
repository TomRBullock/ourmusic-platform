package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.vo.request.room.RoomSetupVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomSetupServiceImpl implements RoomSetupService{

    private final RoomRepository roomRepository;

    @Override
    public void createNewRoom(String userId, RoomSetupVO setupVO) {
        Room room = new Room();
        room.setHostId(userId);
        room.setRoomName(setupVO.getRoomName());
        room.setDescription(setupVO.getDescription());

        String password = setupVO.getPassword();
        if (password != null && !password.isEmpty()) {
            room.setPassword(password);
            room.setPasswordProtected(true);
        }

        room.setCode(generateRoomCode());

        roomRepository.insert(room);
    }

    private String generateRoomCode() {
        return RandomStringUtils.randomAlphanumeric(6);
    }
}
