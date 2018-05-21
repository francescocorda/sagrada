package it.polimi.ingsw.Server;


import it.polimi.ingsw.ClientController;
import it.polimi.ingsw.PlayerData;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.ConnectionMode;
import it.polimi.ingsw.connection.ConnectionRMI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServerImplementation extends UnicastRemoteObject implements
        RMIServerInterface {
    private PlayerData players;
    private ArrayList<ConnectionRMI> connections;

    protected RMIServerImplementation() throws RemoteException {
        super(0);
        connections = new ArrayList<>();
        players = PlayerData.getPlayerData();
    }

    public void addClient(RMIClientInterface client) throws RemoteException {
        //TODO
        Connection connection = new ConnectionRMI(client);
        connections.add((ConnectionRMI) connection);
        Runnable clientController = new ClientController(connection , ConnectionMode.RMI, players);
        Thread thread = new Thread(clientController);
        thread.start();
    }

    public void send(RMIClientInterface client, String message) throws RemoteException {
        for(ConnectionRMI connection : connections){
            if(connection.getClientInterface().equals(client)){
                connection.setCurrentMessage(message);
            }
        }
    }

    public void removeConnection(Connection connection){
        connections.remove(connection);
    }

}
