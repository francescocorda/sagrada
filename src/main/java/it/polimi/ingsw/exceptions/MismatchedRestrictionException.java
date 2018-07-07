package it.polimi.ingsw.exceptions;

public class MismatchedRestrictionException extends Exception {

    /**
     * Constructs a new exception with "The dice doesn't match the pattern restriction." as its detail message.
     */
    public MismatchedRestrictionException() {
        super("The dice doesn't match the pattern restriction.");
    }
}
