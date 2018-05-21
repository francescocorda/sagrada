package it.polimi.ingsw.Server;

import it.polimi.ingsw.client.RMI.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote{

    void send(RMIClientInterface client, String message) throws RemoteException;

    void addClient(RMIClientInterface client) throws RemoteException;

}
