package it.polimi.ingsw.Server;

import it.polimi.ingsw.RMIServerInterface;
import it.polimi.ingsw.RMIServerImplementation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer{
private int RMI_PORT;

    RMIServer(int RMI_PORT) {
        this.RMI_PORT = RMI_PORT;
            try {
                LocateRegistry.createRegistry(RMI_PORT);
                System.out.println("RMI Registry listening on port " + RMI_PORT);
            } catch (RemoteException e1) {
                System.out.println("Registry already up");
            }
            try {
                RMIServerInterface clientHandlerRMI = new RMIServerImplementation();
                Naming.rebind("//localhost/ClientHandler", clientHandlerRMI);
            } catch (MalformedURLException e) {
                System.err.println("Impossible object registration!");
            } catch (RemoteException e) {
                System.err.println("Connection error: " + e.getMessage() + "!");
            }
    }
}