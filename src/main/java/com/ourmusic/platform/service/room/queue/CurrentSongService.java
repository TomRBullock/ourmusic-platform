package com.ourmusic.platform.service.room.queue;

import com.ourmusic.platform.model.submodel.PlayingSongElement;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentSongService {

    private final SimpMessagingTemplate template;

    public void sendMessage(String roomCode, PlayingSongElement playingSongElement){
        this.template.convertAndSend("/topic/"+ roomCode +"/current-song",  playingSongElement);
    }

}
