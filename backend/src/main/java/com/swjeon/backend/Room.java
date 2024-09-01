package com.swjeon.backend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    private String id; // 방의 고유 ID
    private String title; // 방 제목
    private int participantCount; // 참가자 수

    public Room(String id, String title) {
        this.id = id;
        this.title = title;
        this.participantCount = 0; // 초기 참가자는 0명
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", participantCount=" + participantCount +
                '}';
    }
}

