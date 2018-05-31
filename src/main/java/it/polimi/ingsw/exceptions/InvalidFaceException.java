package it.polimi.ingsw.exceptions;

public class InvalidFaceException extends Exception {

    public InvalidFaceException() {
        super("Value not compatible with any dice's face.");
    }
}
