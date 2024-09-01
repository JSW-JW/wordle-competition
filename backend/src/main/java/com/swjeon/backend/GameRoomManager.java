package com.swjeon.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GameRoomManager {

    private Map<String, List<String>> rooms = new ConcurrentHashMap<>();

    public synchronized void addUserToRoom(String roomId, String userId) {
        rooms.putIfAbsent(roomId, new CopyOnWriteArrayList<>());
        List<String> users = rooms.get(roomId);
        users.add(userId);

        log.info("roomID = {}, userID = {}", roomId, userId);
        // 유저가 4명에 도달하면 게임 시작
        if (users.size() == 4) {
            startGameForRoom(roomId);
        }
    }

    public synchronized void removeUserFromRoom(String roomId, String userId) {
        List<String> users = rooms.get(roomId);
        if (users != null) {
            users.remove(userId);
            if (users.isEmpty()) {
                rooms.remove(roomId); // 룸이 비어 있으면 제거
            }
        }
    }

    private void startGameForRoom(String roomId) {

        // 룸의 모든 유저에게 게임 시작 메시지 브로드캐스트
        // 클라이언트에게 메시지 전송 (WebSocket)
        // 예: messagingTemplate.convertAndSend("/topic/room/" + roomId, "Game Start!");
    }
}
