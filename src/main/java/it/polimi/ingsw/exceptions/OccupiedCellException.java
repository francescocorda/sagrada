package it.polimi.ingsw.exceptions;

public class OccupiedCellException extends Exception {
    public OccupiedCellException() {
        super("The cell is already occupied.");
    }
}
