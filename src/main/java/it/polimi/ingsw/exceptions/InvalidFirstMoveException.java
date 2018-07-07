package it.polimi.ingsw.exceptions;

public class InvalidFirstMoveException extends Exception {

    /**
     * Constructs a new exception with "Each player’s first dice of the game must be placed on an edge
     * or corner space,\n" + "respecting all the other restrictions" as its detail message.
     */
    public InvalidFirstMoveException() {
        super("Each player’s first dice of the game must be placed on an edge or corner space,\n" +
                "respecting all the other restrictions");
    }
}
