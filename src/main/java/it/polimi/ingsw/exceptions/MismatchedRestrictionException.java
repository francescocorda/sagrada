package it.polimi.ingsw.exceptions;

public class MismatchedRestrictionException extends Exception {
    public MismatchedRestrictionException() {
        super("You can't place the dice here.");
    }
}
