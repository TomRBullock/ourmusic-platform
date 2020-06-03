package com.ourmusic.platform.websocket;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.model.submodel.QueueElement;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueSocket extends BaseSocket {


    public void sendMessage(Room room){

        List<QueueElement> sortedQueue = room.getQueue().stream()
                .sorted(Comparator.comparingInt(QueueElement::getVotes).reversed())
                .collect(Collectors.toList());

        super.sendMessage("/topic/"+ room.getCode() +"/queue", sortedQueue);
    }

}
