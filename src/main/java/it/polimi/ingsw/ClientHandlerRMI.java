package it.polimi.ingsw;

import it.polimi.ingsw.Server.ServerMain;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.VirtualView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import static it.polimi.ingsw.connection.ConnectionMode.RMI;

public class ClientHandlerRMI extends UnicastRemoteObject implements ClientHandlerInterface {
    private PlayerDatabase playerDatabase;
    private Timer timer;

    public ClientHandlerRMI() throws RemoteException {
        super(0);
        playerDatabase = PlayerDatabase.getPlayerDatabase();
        timer = new Timer();
    }

    public void login(String username, String password, RMIClientInterface client)throws NotValidInputException, RemoteException {

        System.out.println("Client number "+ ServerMain.getServerMain().getNewClientNumber()+" connected through RMI");
        if (playerDatabase.check(username, password, RMI)) {
            System.out.println("User: "+username+" logged in.");
            playerDatabase.addRMIClient(username, client);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        client.checkConnection();
                    } catch (RemoteException e) {
                        playerDatabase.disconnect(username);
                        VirtualView virtualView = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(username);
                        virtualView.notifyObservers("quit");
                        this.cancel();
                    }
                }
            }, 1000, 1000);
        } else {
            throw new NotValidInputException();
        }
    }

    public void update(String message) throws RemoteException{
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
        VirtualView virtualView = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(commands.get(0));
        virtualView.notifyObservers(message);
    }

    public void joinLobby(String username, long time) throws NotValidInputException, RemoteException {
        long systemTime = System.currentTimeMillis()/1000; //current unix time in seconds
        System.out.println("JOIN LOBBY: username: "+username+"\n \t time: "+time);
        if (systemTime > time) {
            Lobby.getLobby().addPlayer(username, time);
        } else throw new NotValidInputException();
    }
}
