package it.polimi.ingsw.exceptions;

public class ImpossibleMoveException extends Exception {

    /**
     * Constructs a new exception with "Impossible to proceed. End of the move." as its detail message.
     */
    public ImpossibleMoveException() {
        super("Impossible to proceed. End of the move.");
    }
}
