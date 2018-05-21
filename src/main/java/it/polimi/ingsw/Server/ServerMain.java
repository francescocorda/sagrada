package it.polimi.ingsw.Server;

import it.polimi.ingsw.Lobby;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class ServerMain {

    private static boolean SERVER_UP;
    private static SocketServer serverSoket;
    private static int SOCKET_PORT;
    private static int RMI_PORT;

    public static void main( String[] args ) {

        //Initialize all the structure for the game/server
        Scanner scanner = new Scanner(System.in);
        SERVER_UP=true;
        System.out.println("Set socket port (type 0 for default value: 3001): ");
        String text = scanner.nextLine();
        int temp;
        if((temp=Integer.valueOf(text)) == 0)
            SOCKET_PORT =3001;
        else
            SOCKET_PORT=temp;
        System.out.println("Set RMI Server port (type 0 for default value: 1099): ");
        text = scanner.nextLine();
        if((temp=Integer.valueOf(text)) == 0)
            RMI_PORT = 1099;
        else
            RMI_PORT = temp;
        Lobby.getLobby();
        start();
    }

    private static void start(){
        rmiServer();
        serverSoket = new SocketServer(SOCKET_PORT);

    }

    public static boolean getStatus(){
        return SERVER_UP;
    }

    private static void rmiServer(){

        try {
            LocateRegistry.createRegistry(RMI_PORT);
            System.out.println("RMI Registry listening on port "+RMI_PORT);

        } catch (RemoteException e) {
            System.out.println("Registry already up");
        }

        try {
            RMIServerImplementation rmiServer = new RMIServerImplementation();
            Naming.rebind("//localhost/SagradaRMIServer", rmiServer);

        } catch (MalformedURLException e) {
            System.err.println("Impossible object registration!");
        } catch (RemoteException e) {
            System.err.println("Connection error: " + e.getMessage() + "!");
        }
    }
}
