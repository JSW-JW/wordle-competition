import React, { useState, useEffect } from 'react';
import axios from 'axios';

const RoomList = ({ onJoinRoom }) => {
    const [rooms, setRooms] = useState([]); // 방 목록 상태

    useEffect(() => {
        fetchRooms(); // 초기 방 목록 조회
    }, []);

    // 방 목록 조회 API 호출 함수
    const fetchRooms = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/rooms');
            setRooms(response.data);
        } catch (error) {
            console.error('Error fetching rooms:', error);
        }
    };

    return (
        <div>
            <h2>Join Room Page</h2>
            <div className="room-list">
                {rooms.map((room) => (
                    <div key={room.id} className="room-card" onClick={() => onJoinRoom(room.id)}>
                        <div className="room-title">{room.title}</div>
                        <div className="room-info">{room.participantCount} Participants</div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default RoomList;