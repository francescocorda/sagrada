package it.polimi.ingsw.exceptions;

public class RollBackException extends Exception {
        public RollBackException() {
            super("Impossible to proceed. Replay of the previous step.");
        }
}
