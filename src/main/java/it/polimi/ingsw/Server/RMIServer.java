package it.polimi.ingsw.Server;

import it.polimi.ingsw.RMIServerInterface;
import it.polimi.ingsw.RMIServerImplementation;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    RMIServer(int rmiPort) throws NetworkErrorException {
        try {
            LocateRegistry.createRegistry(rmiPort);
            System.out.println("RMI Registry listening on port " + rmiPort);
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