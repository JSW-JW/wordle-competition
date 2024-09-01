package com.swjeon.backend;

public class Guess {
    private String userId;
    private String word;

    // Constructors, getters, setters

    public Guess() {}

    public Guess(String userId, String word) {
        this.userId = userId;
        this.word = word;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
