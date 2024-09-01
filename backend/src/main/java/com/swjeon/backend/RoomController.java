package com.swjeon.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private List<Room> rooms = new ArrayList<>(); // 임시로 방 목록을 메모리에 저장

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

    @GetMapping
    public List<Room> getRooms() {
        // 현재 생성된 모든 방을 반환
        return rooms;
    }
}

