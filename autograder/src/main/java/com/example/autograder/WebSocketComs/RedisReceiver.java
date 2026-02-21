package com.example.autograder.WebSocketComs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisReceiver {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void receiveMessage(String message) {
        System.out.println("Gateway received from Redis: " + message);

        // Push to User via WebSocket
        // "/topic/updates" is where the Frontend listens
        messagingTemplate.convertAndSend("/topic/updates", message);
    }
}
