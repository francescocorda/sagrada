package it.polimi.ingsw.exceptions;

public class InvalidNeighboursException extends Exception {
    public InvalidNeighboursException() {
        super("The position isn't near any valid neighbour.");
    }
}
