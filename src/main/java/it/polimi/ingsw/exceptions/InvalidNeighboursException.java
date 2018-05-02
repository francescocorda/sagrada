package it.polimi.ingsw.exceptions;

public class InvalidNeighboursException extends Exception {
    public InvalidNeighboursException() {
        super("There is no valid neighbour.");
    }
}
