package it.polimi.ingsw.Server;

import it.polimi.ingsw.ClientHandlerInterface;
import it.polimi.ingsw.ClientHandlerRMI;
import it.polimi.ingsw.PlayerDatabase;

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
                ClientHandlerInterface clientHandlerRMI = new ClientHandlerRMI();
                Naming.rebind("//localhost/ClientHandler", clientHandlerRMI);
            } catch (MalformedURLException e) {
                System.err.println("Impossible object registration!");
            } catch (RemoteException e) {
                System.err.println("Connection error: " + e.getMessage() + "!");
            }
    }
}