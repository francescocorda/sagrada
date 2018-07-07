package it.polimi.ingsw.exceptions;

public class InvalidFaceException extends Exception {

    /**
     * Constructs a new exception with "Value not compatible with any dice's face." as its detail message.
     */
    public InvalidFaceException() {
        super("Value not compatible with any dice's face.");
    }
}
