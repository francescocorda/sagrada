package it.polimi.ingsw.exceptions;

public class InvalidFaceException extends Exception {

    public InvalidFaceException() {
        super("The value provided is not compatible with any dice's face.");
    }
}
