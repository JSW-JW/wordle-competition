import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const GameComponent = () => {
    const [stompClient, setStompClient] = useState(null);
    const [roomId, setRoomId] = useState('room1'); // 기본적으로 room1 사용
    const [userId, setUserId] = useState('user1'); // 기본적으로 user1 사용
    const [isConnected, setIsConnected] = useState(false); // WebSocket 연결 상태 추적

    useEffect(() => {
        // SockJS와 STOMP를 사용하여 WebSocket 연결 설정
        const socket = new SockJS('http://localhost:8080/ws');
        const client = Stomp.over(socket);

        client.connect({}, () => {
            console.log('Connected to WebSocket server');

            client.subscribe(`/topic/room/${roomId}`, (message) => {
                console.log('Received message from server:', message.body);
            });

            setStompClient(client);
            setIsConnected(true); // 연결 상태 설정
        });

        return () => {
            if (stompClient) {
                stompClient.disconnect();
            }
        };
    }, []); // 빈 배열로 설정하여 처음 마운트될 때만 실행되도록 함

    const handleJoinRoom = () => {
        if (stompClient && isConnected) {
            sendJoinRequest(stompClient, roomId, userId);
        }
    };

    const sendJoinRequest = (client, roomId, userId) => {
        client.send('/app/join', {}, JSON.stringify({ roomId, userId }));
    };

    return (
        <div>
            <h1>WebSocket Game</h1>
            <p>Room ID: {roomId}</p>
            <p>User ID: {userId}</p>
            <button onClick={handleJoinRoom}>Join Room</button>
        </div>
    );
};

export default GameComponent;