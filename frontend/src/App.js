import React, { useState } from 'react';
import RoomList from './component/RoomList';
import RoomComponent from './component/RoomComponent';

const App = () => {
    const [selectedRoomId, setSelectedRoomId] = useState(null);

    const handleJoinRoom = (roomId) => {
        setSelectedRoomId(roomId);
    };

    return (
        <div>
            {selectedRoomId ? (
                <RoomComponent roomId={selectedRoomId} />
            ) : (
                <RoomList onJoinRoom={handleJoinRoom} />
            )}
        </div>
    );
};

export default App;