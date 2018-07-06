package it.polimi.ingsw.server.rmi;

import it.polimi.ingsw.client.connection_mode.rmi.RMIClientInterface;
import it.polimi.ingsw.exceptions.NotValidInputException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {

    /**
     * checks if the given credentials are correct, if so it logs-in the player.
     * @param username : the given {@link String} username
     * @param password : the given {@link String} password
     * @param client : the given {@link RMIClientInterface}
     * @throws NotValidInputException if the given credentials are wrong
     * @throws RemoteException if any connection error occurs
     */
    void login(String username, String password, RMIClientInterface client) throws RemoteException, NotValidInputException;

    /**
     * adds the player to the {@link it.polimi.ingsw.server.Lobby} if the given time is acceptable.
     * @param username : the given {@link String} username
     * @param time : the given {@link Long} time
     * @throws NotValidInputException if the given time is greater than the system current UNIX time
     * @throws RemoteException if any connection error occurs
     */
    void joinLobby(String username, long time) throws RemoteException, NotValidInputException;

    /**
     * updates the Server with the given {@link String} message.
     * from the given {@link String} message
     * @param message: the given {@link String} message
     * @throws RemoteException if any connection error occurs
     */
    void update(String message) throws RemoteException;
}
