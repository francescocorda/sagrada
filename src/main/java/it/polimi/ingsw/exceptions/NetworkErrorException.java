package it.polimi.ingsw.exceptions;

public class NetworkErrorException extends Exception {
    public NetworkErrorException() {
        super("The dice doesn't match the pattern restriction.");
    }
}

