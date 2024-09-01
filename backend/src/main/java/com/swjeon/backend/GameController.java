package com.swjeon.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @Autowired
    private GameRoomManager gameRoomManager;

    @MessageMapping("/join")
    @SendTo("/topic/room/{roomId}")
    public void joinRoom(JoinRequestDto joinRequest) {
        String roomId = joinRequest.getRoomId();
        String userId = joinRequest.getUserId();
        gameRoomManager.addUserToRoom(roomId, userId);
    }

    @MessageMapping("/leave")
    public void leaveRoom(String roomId, String userId) {
        gameRoomManager.removeUserFromRoom(roomId, userId);
    }
}
