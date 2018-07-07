package it.polimi.ingsw.exceptions;

public class DuplicateException extends Exception{

    /**
     * Constructs a new exception with "Duplicate exception." as its detail message.
     */
    public DuplicateException() {
        super("Duplicate exception.");
    }
}
