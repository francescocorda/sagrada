package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientHandlerInterface extends Remote {
    void login(String username, String password) throws RemoteException, NotValidInputException;
    void joinLobby(String username, long time) throws RemoteException, NotValidInputException;
}
