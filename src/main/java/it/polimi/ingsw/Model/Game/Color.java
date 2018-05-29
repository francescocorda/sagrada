package it.polimi.ingsw.Model.Game;

import java.io.Serializable;

public enum Color implements Serializable {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m");
    public static final String RESET = "\u001B[0m";
    private String escape;
    public static final int NUMBER_OF_COLORS = 5;

    Color(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }
}