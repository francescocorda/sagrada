package it.polimi.ingsw.exceptions;

public class MismatchedRestrictionException extends Exception {
    public MismatchedRestrictionException() {
        super("The dice doesn't match the pattern restriction.");
    }
}
