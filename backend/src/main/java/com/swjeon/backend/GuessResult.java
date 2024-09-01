package com.swjeon.backend;

import java.util.List;

public class GuessResult {
    private String userId;
    private String word;
    private List<Integer> correctPositions;
    private List<Integer> incorrectPositions;

    // Constructors, getters, setters

    public GuessResult() {}

    public GuessResult(String userId, String word, List<Integer> correctPositions, List<Integer> incorrectPositions) {
        this.userId = userId;
        this.word = word;
        this.correctPositions = correctPositions;
        this.incorrectPositions = incorrectPositions;
    }

    public GuessResult toObscuredResult() {
        return new GuessResult(this.userId, "*****", List.of(), List.of());
    }

    // Getters and setters
}
