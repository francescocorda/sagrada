package it.polimi.ingsw.server.rmi;

import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

    private RMIServerInterface clientHandlerRMI;
    private static final  String LISTENING_MESSAGE = "rmi Registry listening on port ";
    private static final String REGISTRY_UP_MESSAGE = "Registry already up";
    private static final String SERVER_IP = "localhost";
    private static final String CLIENT_HANDLER = "ClientHandler";
    private static final String ERROR_MESSAGE = "Connection error!";

    /**
     * creates an {@link RMIServer} given an {@link Integer} rmiPort.
     * @param rmiPort : the given port for this {@link RMIServer}
     * @throws NetworkErrorException if a {@link RemoteException} or a {@link MalformedURLException} is thrown
     */
    public RMIServer(int rmiPort) throws NetworkErrorException {
        try {
            LocateRegistry.createRegistry(rmiPort);
            System.out.println(LISTENING_MESSAGE + rmiPort);
        } catch (RemoteException e1) {
            System.out.println(REGISTRY_UP_MESSAGE);
        }
        try {
            clientHandlerRMI = new RMIServerImplementation();
            Naming.rebind("//"+SERVER_IP+":"+rmiPort+"/"+ CLIENT_HANDLER, clientHandlerRMI);
        } catch (RemoteException | MalformedURLException e) {
            System.out.println(ERROR_MESSAGE);
            throw new NetworkErrorException();
        }
    }

    /**
     * closes this {@link RMIServer}.
     * it does that by loosing the reference of {@link RMIServerInterface} allowing it to be garbage collected
     */
    public void close(){
        clientHandlerRMI=null;
    }
}