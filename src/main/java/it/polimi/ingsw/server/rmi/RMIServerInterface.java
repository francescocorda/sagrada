package it.polimi.ingsw.server.rmi;

import it.polimi.ingsw.client.connection_mode.rmi.RMIClientInterface;
import it.polimi.ingsw.exceptions.NotValidInputException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
    void login(String username, String password, RMIClientInterface client) throws RemoteException, NotValidInputException;
    void joinLobby(String username, long time) throws RemoteException, NotValidInputException;
    void update(String message) throws RemoteException;
}
