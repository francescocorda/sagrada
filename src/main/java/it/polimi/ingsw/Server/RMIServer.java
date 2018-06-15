package it.polimi.ingsw.Server;

import it.polimi.ingsw.RMIServerInterface;
import it.polimi.ingsw.RMIServerImplementation;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

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
            Naming.rebind("//localhost/ClientHandler", clientHandlerRMI);
        } catch (MalformedURLException e) {
            System.err.println("Impossible object registration!");
            throw new NetworkErrorException();
        } catch (RemoteException e) {
            System.err.println("Connection error: " + e.getMessage() + "!");
            throw new NetworkErrorException();
        }
    }
}