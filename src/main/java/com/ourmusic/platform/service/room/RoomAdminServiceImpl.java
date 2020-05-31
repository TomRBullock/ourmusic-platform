package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.service.spotify.SpotifyPlayerService;
import com.ourmusic.platform.vo.request.room.RoomSetupVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomAdminServiceImpl implements RoomAdminService {

    private final RoomRepository roomRepository;
    private final SpotifyPlayerService spotifyPlayerService;

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
        return RandomStringUtils.randomAlphanumeric(8);
    }

    @Override
    public boolean deleteRoom(String userId, String roomCode) {
        long numDeleted = roomRepository.deleteByCodeAndHostId(roomCode, userId);
        if (numDeleted > 0L) {
            return true;
        }
        return false;
    }

    @Override
    public void activateRoom(String userId, String roomCode) {
        Optional<Room> currentActiveRoom = roomRepository.findByHostIdAndActiveIsTrue(userId);
        updateActiveStatus(currentActiveRoom, userId);

        Optional<Room> existingRoom = roomRepository.findByCode(roomCode);
        updateActiveStatus(existingRoom, userId);
    }

    private void updateActiveStatus(Optional<Room> roomOpt, String userId) {
        roomOpt.ifPresent(room -> {
            if(room.getHostId().equals(userId)) {
                room.setActive(!room.isActive());
                roomRepository.save(room);
            }
        });
    }

    @Override
    public ResponseEntity<Boolean> togglePlayPause(String userId) {

        Optional<Room> activeRoomOpt = roomRepository.findByHostIdAndActiveIsTrue(userId);

        if (!activeRoomOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        boolean toggled = spotifyPlayerService.togglePlayPause(userId,  activeRoomOpt.get().isPlay());
        return ResponseEntity.ok(toggled);
    }

    @Override
    public List<Room> getUserRooms(String userId) {
        return roomRepository.findAllByHostId(userId);
    }
}
