package it.polimi.ingsw.client.RMI;

import java.rmi.RemoteException;

public class RMIClientImplementation implements RMIClientInterface {

    public void send(String message) throws RemoteException{
        System.out.println(message);
    }

    public void checkConnection()  throws RemoteException{
        //This method has only to check the connection between client and server, so it has to be empty
    }
}
