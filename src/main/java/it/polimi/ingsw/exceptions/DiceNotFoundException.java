package it.polimi.ingsw.exceptions;

public class DiceNotFoundException extends Exception{

    /**
     * Constructs a new exception with "Dice not found." as its detail message.
     */
    public DiceNotFoundException() {
        super("Dice not found.");
    }
}
