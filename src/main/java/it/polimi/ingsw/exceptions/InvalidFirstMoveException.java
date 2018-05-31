package it.polimi.ingsw.exceptions;

public class InvalidFirstMoveException extends Exception {

    public InvalidFirstMoveException() {
        super("Each player’s first dice of the game must be placed on an edge or corner space,\n" +
                "respecting all the other restrictions");
    }
}
