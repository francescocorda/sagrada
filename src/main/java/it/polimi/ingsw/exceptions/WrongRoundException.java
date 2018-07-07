package it.polimi.ingsw.exceptions;

public class WrongRoundException extends Exception {

    /**
     * Constructs a new exception with "You can not make this action in this round." as its detail message.
     */
    public WrongRoundException() {
        super("You can not make this action in this round.");
    }
}
