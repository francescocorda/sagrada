package it.polimi.ingsw;

public enum Restriction {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ONE("\u2680"),
    TWO("\u2681"),
    THREE("\u2682"),
    FOUR("\u2683"),
    FIVE("\u2684"),
    SIX("\u2685");

    static final String RESET = "\u001B[0m";
    private String escape;

    Restriction(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }
}
