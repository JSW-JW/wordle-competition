import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const GameComponent = () => {
    const [stompClient, setStompClient] = useState(null);
    const [guesses, setGuesses] = useState([]);
    const [myGuesses, setMyGuesses] = useState([]);
    const [gameState, setGameState] = useState({ started: false });

    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws');
        const client = Stomp.over(socket);

        client.connect({}, () => {
            client.subscribe('/topic/game', (message) => {
                setGameState(JSON.parse(message.body));
            });

            client.subscribe('/topic/guesses', (message) => {
                setGuesses(JSON.parse(message.body).guesses);
            });

            setStompClient(client);
        });

        return () => {
            if (stompClient) stompClient.disconnect();
        };
    }, []);

    const sendGuess = (word) => {
        if (stompClient && gameState.started) {
            stompClient.send('/app/guess', {}, JSON.stringify({ userId: 'user1', word }));
            setMyGuesses([...myGuesses, word]);
        }
    };

    const startGame = () => {
        if (stompClient) {
            stompClient.send('/app/start', {}, {});
        }
    };

    return (
        <div>
            <h1>Wordle Competition</h1>
            {gameState.started ? <p>Game in progress...</p> : <button onClick={startGame}>Start Game</button>}

            <div>
                <h2>Your Guesses</h2>
                <ul>
                    {myGuesses.map((guess, index) => (
                        <li key={index}>{guess}</li>
                    ))}
                </ul>
            </div>

            <div>
                <h2>Other Players' Guesses</h2>
                <ul>
                    {guesses.map((guess, index) => (
                        <li key={index}>User {guess.userId} guessed: {'*****'.repeat(5)}</li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default GameComponent;
