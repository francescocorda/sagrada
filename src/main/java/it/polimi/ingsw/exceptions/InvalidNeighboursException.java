package it.polimi.ingsw.exceptions;

public class InvalidNeighboursException extends Exception {

    /**
     * Constructs a new exception with "The position isn't near any valid neighbour." as its detail message.
     */
    public InvalidNeighboursException() {
        super("The position isn't near any valid neighbour.");
    }
}
