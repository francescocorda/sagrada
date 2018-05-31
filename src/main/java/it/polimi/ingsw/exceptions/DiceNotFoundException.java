package it.polimi.ingsw.exceptions;

public class DiceNotFoundException extends Exception{
    public DiceNotFoundException() {
        super("Dice not found.");
    }
}
