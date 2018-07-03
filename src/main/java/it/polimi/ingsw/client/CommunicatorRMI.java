package it.polimi.ingsw.client;

import it.polimi.ingsw.RMIServerInterface;
import it.polimi.ingsw.client.RMI.RMIClientImplementation;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.View;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CommunicatorRMI implements Communicator {
    private View view;
    private RMIClientInterface client;
    private RMIServerInterface server;

    /**
     * creates a {@link CommunicatorRMI} Object from a given {@link #view}.
     * @param view is the given view on witch the {@link CommunicatorRMI} operates
     */
    public CommunicatorRMI(View view) {
        this.view = view;
    }

    /**
     * initialise RMI communication's field to allow communication with the {@link #server}.
     * It does that by getting all the needed field from a given {@link ArrayList<String>}:
     * the first parameter should be the server's address (IP) while the second should be
     * the port to connect with it
     * @param IPaddress is the address of the server
     * @param port is the port of the server
     * @throws NetworkErrorException if  a  {@link RemoteException} or a {@link NotBoundException}
     * is thrown
     */
    @Override
    public void initialize(String IPaddress, int port) throws NetworkErrorException{
        try {
            this.client = (RMIClientInterface) UnicastRemoteObject.exportObject(new RMIClientImplementation(view), 0);
            this.server = (RMIServerInterface) Naming.lookup("//" + IPaddress+":"+ port + "/ClientHandler");
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * sends a login message to the {@link #server}.
     * it does that through the use of method {@link RMIServerInterface#login(String, String, RMIClientInterface)}
     * and the given parameters
     * @param username is the parameter for user's username
     * @param password is the parameter for user's password
     * @throws NetworkErrorException if  a  {@link RemoteException} or a {@link NotBoundException}
     * is thrown
     * @throws NotValidInputException if the given parameters aren't correct
     */
    @Override
    public void login(String username, String password) throws NetworkErrorException, NotValidInputException {
        try {
            server.login(username, password, client);
        } catch (RemoteException | NullPointerException e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * sends a message to the {@link #server} to join the {@link it.polimi.ingsw.Lobby}.
     * it does that through te use of method {@link RMIServerInterface#joinLobby(String, long)} and
     * the given parameters.
     * @param username is the parameter for user's username
     * @param time is the parameter for user's last access to a cathedral
     * @throws NetworkErrorException if  a  {@link RemoteException} or a {@link NotBoundException}
     * is thrown
     * @throws NotValidInputException if the given time is not valid
     */
    @Override
    public void lobby(String username, long time) throws NetworkErrorException, NotValidInputException {
        try {
            server.joinLobby(username, time);
        } catch (RemoteException | NullPointerException e) {
            throw new NetworkErrorException();
        } catch (NotValidInputException e) {
            throw new NotValidInputException();
        }
    }

    /**
     * it sends a message to the {@link #server}.
     * it does that through the use of method {@link RMIServerInterface#update(String)}
     * and a given message
     * @param message is the given message to be sent to {@link #server}
     * @throws NetworkErrorException if  a  {@link RemoteException} or a {@link NotBoundException}
     * is thrown
     */
    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try {
            server.update(view.getUsername()+"/"+message);
        } catch (RemoteException | NullPointerException e) {
            throw new NetworkErrorException();
        }
    }
}
