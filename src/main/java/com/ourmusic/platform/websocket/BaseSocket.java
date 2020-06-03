package com.ourmusic.platform.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public abstract class BaseSocket {

    @Autowired SimpMessagingTemplate template;

    protected void sendMessage(String url, Object body){
        this.template.convertAndSend(url,  body);
    }
}
