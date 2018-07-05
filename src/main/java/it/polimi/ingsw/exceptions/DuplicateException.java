package it.polimi.ingsw.exceptions;

public class DuplicateException extends Exception{

    public DuplicateException() {
        super("Dice not found.");
    }
}
