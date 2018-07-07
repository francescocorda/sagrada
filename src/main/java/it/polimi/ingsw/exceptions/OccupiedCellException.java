package it.polimi.ingsw.exceptions;

public class OccupiedCellException extends Exception {

    /**
     * Constructs a new exception with "Position already occupied." as its detail message.
     */
    public OccupiedCellException() {
        super("Position already occupied.");
    }
}
