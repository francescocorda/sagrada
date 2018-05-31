package it.polimi.ingsw.exceptions;

public class OccupiedCellException extends Exception {
    public OccupiedCellException() {
        super("Position already occupied.");
    }
}
