package it.polimi.ingsw.exceptions;

public class NotValidInputException extends Exception {

    /**
     * Constructs a new exception with "Input parameter not valid." as its detail message.
     */
    public NotValidInputException(){
        super("Input parameter not valid.");
    }
}
