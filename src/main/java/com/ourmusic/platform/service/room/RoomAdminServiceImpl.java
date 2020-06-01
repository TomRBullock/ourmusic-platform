package com.ourmusic.platform.service.room;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.repository.RoomRepository;
import com.ourmusic.platform.service.room.queue.QueueSchedule;
import com.ourmusic.platform.service.room.queue.QueueService;
import com.ourmusic.platform.service.spotify.SpotifyPlayerService;
import com.ourmusic.platform.vo.request.room.RoomSetupVO;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
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
    private final QueueService queueService;

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
        return RandomStringUtils.randomAlphanumeric(6,9).toUpperCase();
    }

    @Override
    public boolean deleteRoom(String userId, String roomId) {
        long numDeleted = roomRepository.deleteByIdAndHostId(roomId, userId);
        if (numDeleted > 0L) {
            return true;
        }
        return false;
    }

    @Override
    public void toggleRoomActivation(String userId, String roomId) {

        CurrentlyPlayingContext usersCurrentPlayback = spotifyPlayerService.getUsersCurrentPlayback(userId);
        if (usersCurrentPlayback.getIs_playing()) {
            togglePlayPause(userId);
        }

        Optional<Room> currentActiveRoom = roomRepository.findByHostIdAndActiveIsTrue(userId);
        updateActiveStatus(currentActiveRoom, userId);

        if (currentActiveRoom.isPresent() && currentActiveRoom.get().getId().equals(roomId)) {
            return;
        }

        Optional<Room> existingRoom = roomRepository.findById(roomId);
        updateActiveStatus(existingRoom, userId);
    }

    private void updateActiveStatus(Optional<Room> roomOpt, String userId) {
        roomOpt.ifPresent(room -> {
            if(room.getHostId().equals(userId)) {
                room.setActive(!room.isActive());
                room.setPlay(false);
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

        Room room = activeRoomOpt.get();
        boolean toggled = spotifyPlayerService.togglePlayPause(userId,  room.isPlay());
        if (toggled) {
            boolean newPlayState = !room.isPlay();

            room.setPlay(newPlayState);
            roomRepository.save(room);

            if (newPlayState) {
                queueService.startQueue(room);
            } else {
                queueService.stopQueue(room);
            }
        }

        return ResponseEntity.ok(toggled);
    }

    @Override
    public List<Room> getUserRooms(String userId) {
        return roomRepository.findAllByHostId(userId);
    }
}
