package it.polimi.ingsw.server.rmi;

import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

    public RMIServer(int rmiPort) throws NetworkErrorException {
        try {
            LocateRegistry.createRegistry(rmiPort);
            System.out.println("rmi Registry listening on port " + rmiPort);
        } catch (RemoteException e1) {
            System.out.println("Registry already up");
        }
        try {
            RMIServerInterface clientHandlerRMI = new RMIServerImplementation();
            Naming.rebind("//localhost:"+rmiPort+"/ClientHandler", clientHandlerRMI);
        } catch (RemoteException | MalformedURLException e) {
            System.err.println("Connection error: " + e.getMessage() + "!");
            throw new NetworkErrorException();
        }
    }
}