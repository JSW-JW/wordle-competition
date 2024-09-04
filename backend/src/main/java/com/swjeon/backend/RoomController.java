package com.swjeon.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private List<Room> rooms = new ArrayList<>(
            List.of(
                    new Room(UUID.randomUUID().toString(), "title 1"),
                    new Room(UUID.randomUUID().toString(), "title 2"),
                    new Room(UUID.randomUUID().toString(), "title 3"),
                    new Room(UUID.randomUUID().toString(), "title 4")
            )
    ); // 임시로 방 목록을 메모리에 저장

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // WebSocket을 통해 메시지를 전송하기 위한 템플릿

    @PostMapping("/create")
    public Room createRoom(@RequestBody Room roomRequest) {
        // 방 생성 로직
        String roomId = UUID.randomUUID().toString(); // 고유한 방 ID 생성
        Room newRoom = new Room(roomId, roomRequest.getTitle());
        rooms.add(newRoom); // 방 목록에 새로운 방 추가

        // logging rooms info
        log.info("Rooms = {}", rooms.toString());
        return newRoom; // 생성된 방 정보 반환
    }

    @PostMapping("/join")
    public Room joinRoom(@RequestParam String roomId) {
        Room room = findRoomById(roomId);
        if (room != null) {
            room.setParticipantCount(room.getParticipantCount() + 1); // 참가자 수 증가
            // WebSocket을 통해 해당 방의 참가자 수 업데이트 브로드캐스트
            messagingTemplate.convertAndSend("/topic/room/" + roomId, room);
        } else {
            log.info("room matching the id not found");
        }
        return room;
    }

    private Room findRoomById(String roomId) {
        return rooms.stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst()
                .orElse(null);
    }

    @GetMapping
    public List<Room> getRooms() {
        // 현재 생성된 모든 방을 반환
        return rooms;
    }
}

