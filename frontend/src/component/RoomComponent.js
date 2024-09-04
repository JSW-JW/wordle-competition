import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import axios from 'axios';

const RoomComponent = ({ roomId }) => {
    const [stompClient, setStompClient] = useState(null);
    const [participantCount, setParticipantCount] = useState(0);

    useEffect(() => {
        // 방에 입장하면 WebSocket 연결 설정
        const socket = new SockJS('http://localhost:8080/ws');
        const client = Stomp.over(socket);

        client.connect({}, () => {
            console.log('Connected to WebSocket server');
            setStompClient(client);

            // 해당 방에 대한 WebSocket 구독 설정
            client.subscribe(`/topic/room/${roomId}`, (message) => {
                const updatedRoom = JSON.parse(message.body);
                setParticipantCount(updatedRoom.participantCount); // 수신된 메시지를 기반으로 상태 업데이트
            });

            // WebSocket 연결이 성공적으로 설정된 후에 방 참여 요청
            joinRoom();
        });

        // 컴포넌트가 언마운트될 때 WebSocket 연결 해제
        return () => {
            if (stompClient) {
                stompClient.disconnect();
            }
        };
    }, [roomId]);

    const joinRoom = async () => {
        try {
            await axios.post(`http://localhost:8080/api/rooms/join`, null, {
                params: { roomId },
            });
        } catch (error) {
            console.error('Error joining room:', error);
        }
    };

    return (
        <div>
            <h2>Room: {roomId}</h2>
            <p>Participants: {participantCount}</p> {/* 참가자 수 업데이트된 값 표시 */}
        </div>
    );
};

export default RoomComponent;
