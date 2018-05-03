package it.polimi.ingsw.exceptions;

public class NotValidInputException extends Exception {
    public NotValidInputException(){
        super("Input parameter not valid.");
    }
}
