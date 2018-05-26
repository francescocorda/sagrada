package it.polimi.ingsw;

import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.exceptions.NotValidInputException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Timer;

public interface ClientHandlerInterface extends Remote {
    void login(String username, String password, RMIClientInterface client) throws RemoteException, NotValidInputException;
    void joinLobby(String username, long time) throws RemoteException, NotValidInputException;
}
