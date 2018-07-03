package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Communicator {
    /**
     * initialise RMI communication's field to allow communication with the server.
     * @param IPaddress is the address of the server
     * @param port is the port of the server
     * @throws NetworkErrorException if  a network related exception is thrown
     */
    void initialize(String IPaddress, int port) throws NetworkErrorException;

    /**
     * sends a login message to the server.
     * @param username is the parameter for user's username
     * @param password is the parameter for user's password
     * @throws NetworkErrorException if  a  network related exception is thrown.
     * @throws NotValidInputException if the given parameters aren't correct
     */
    void login(String username, String password) throws NetworkErrorException, NotValidInputException;

    /**
     * sends a message to the server to join the {@link it.polimi.ingsw.Lobby}.
     * @param username is the parameter for user's username
     * @param time is the parameter for user's last access to a cathedral
     * @throws NetworkErrorException if  a  network related exception is thrown
     * @throws NotValidInputException if the given time is not valid
     */
    void lobby(String username, long time) throws NetworkErrorException, NotValidInputException;

    /**
     * it sends a message to the server.
     * @param message is the given message to be sent to the server
     * @throws NetworkErrorException if a network related exception is thrown
     */
    void sendMessage(String message) throws NetworkErrorException;
}
