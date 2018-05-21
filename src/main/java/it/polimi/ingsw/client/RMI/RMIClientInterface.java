package it.polimi.ingsw.client.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    public void send(String message) throws RemoteException;

}
