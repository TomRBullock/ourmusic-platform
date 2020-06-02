package com.ourmusic.platform.websocket;

import com.ourmusic.platform.model.submodel.PlayingSongElement;
import org.springframework.stereotype.Service;

@Service
public class CurrentSongSocket extends BaseSocket {

    public void sendMessage(String roomCode, PlayingSongElement playingSongElement){
        super.sendMessage("/topic/"+ roomCode +"/current-song",  playingSongElement);
    }

}
