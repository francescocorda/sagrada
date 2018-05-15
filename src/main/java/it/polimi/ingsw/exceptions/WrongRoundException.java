package it.polimi.ingsw.exceptions;

public class WrongRoundException extends Exception {
    public WrongRoundException() {
        super("You can not make this action in this round.");
    }
}
