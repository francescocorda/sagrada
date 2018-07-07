package it.polimi.ingsw.exceptions;

public class RollBackException extends Exception {

    /**
     * Constructs a new exception with "Impossible to proceed. Replay of the previous step." as its detail message.
     */
    public RollBackException() {
        super("Impossible to proceed. Replay of the previous step.");
    }
}
