package it.polimi.ingsw.exceptions;

public class ImpossibleMoveException extends Exception {
        public ImpossibleMoveException() {
            super("Impossible to proceed. End of the move.");
        }
}
