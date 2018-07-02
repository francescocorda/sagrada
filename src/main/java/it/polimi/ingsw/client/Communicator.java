package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Communicator {
    void initialize(ArrayList<String> parameters) throws NetworkErrorException;
    void login(String username, String password) throws NetworkErrorException, NotValidInputException;
    void lobby(String username, long time) throws NetworkErrorException, NotValidInputException;
    void sendMessage(String message) throws NetworkErrorException;
}
