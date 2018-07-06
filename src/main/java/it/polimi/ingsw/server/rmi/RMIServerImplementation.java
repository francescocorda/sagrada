package it.polimi.ingsw.server.rmi;

import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.server.client_handler.ClientHandlerRMI;
import it.polimi.ingsw.database.ClientDatabase;
import it.polimi.ingsw.database.VirtualViewsDataBase;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.client.connection_mode.rmi.RMIClientInterface;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.VirtualView;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RMIServerImplementation extends UnicastRemoteObject implements RMIServerInterface {
    private transient ClientDatabase clientDatabase;

    /**
     * creates a new {@link RMIServerImplementation}
     * @throws RemoteException if any connection error occurs.
     */
    RMIServerImplementation() throws RemoteException {
        super(0);
        clientDatabase = ClientDatabase.getPlayerDatabase();
    }

    /**
     * checks if the given credentials are correct, if so it logs-in the player.
     * it does that by calling method {@link ClientDatabase#check(String, String)} and if the outcome is true
     * a new {@link it.polimi.ingsw.server.client_handler.ClientHandlerRMI} is being created and associated to
     * the {@link it.polimi.ingsw.database.ClientData} corresponding to the given {@link String} username
     * @param username : the given {@link String} username
     * @param password : the given {@link String} password
     * @param client : the given {@link RMIClientInterface}
     * @throws NotValidInputException if the given credentials are wrong
     * @throws RemoteException if any connection error occurs
     */
    public void login(String username, String password, RMIClientInterface client)throws NotValidInputException, RemoteException {
        System.out.println("Client number "+ ServerMain.getServerMain().getNewClientNumber()+" connected through rmi");
        if (clientDatabase.check(username, password)) {
            System.out.println("User: "+username+" logged in.");
            clientDatabase.setClientHandler(username, new ClientHandlerRMI(client));
        } else {
            throw new NotValidInputException();
        }
    }

    /**
     * updates the Server with the given {@link String} message.
     * it does that by calling method {@link VirtualView#notifyObservers(String)} of the corresponding
     * {@link VirtualView} of the username gotten from the first element of the {@link ArrayList<String>} extracted
     * from the given {@link String} message
     * @param message: the given {@link String} message
     * @throws RemoteException if any connection error occurs
     */
    public void update(String message) throws RemoteException{
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
        VirtualView virtualView = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(commands.get(0));
        virtualView.notifyObservers(message);
    }

    /**
     * adds the player to the {@link Lobby} if the given time is acceptable.
     * it does that by comparing system current UNIX time with the given one,
     * if the given one is <= system current UNIX time
     * @param username : the given {@link String} username
     * @param time : the given {@link Long} time
     * @throws NotValidInputException if the given time is greater than the system current UNIX time
     * @throws RemoteException if any connection error occurs
     */
    public void joinLobby(String username, long time) throws NotValidInputException, RemoteException {
        long systemTime = System.currentTimeMillis()/1000; //current unix time in seconds
        if (systemTime > time) {
            Lobby.getLobby().addPlayer(username, time);
        } else throw new NotValidInputException();
    }
}
