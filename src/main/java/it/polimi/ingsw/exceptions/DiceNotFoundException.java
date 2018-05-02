package it.polimi.ingsw.exceptions;

public class DiceNotFoundException extends Exception{
    public DiceNotFoundException() {
        super("There isn't a dice in the selected cell.");
    }
}
