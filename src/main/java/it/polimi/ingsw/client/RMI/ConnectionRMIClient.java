package it.polimi.ingsw.client.RMI;

import it.polimi.ingsw.ClientHandlerRMI;
import it.polimi.ingsw.connection.Connection;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ConnectionRMIClient implements Connection, RMIClientInterface {

    private ArrayList<String> buffer;
   // private RMIServerInterface server;
    private RMIClientInterface remoteRef;

    public ConnectionRMIClient(ClientHandlerRMI server, RMIClientInterface remoteRef) {
        //this.server = server;
        this.remoteRef = remoteRef;
        buffer=new ArrayList<>();
    }

    @Override
    public void sendMessage(String message) {
       /* try {
           server.send(remoteRef, message);
        } catch (RemoteException e) {
            close();
            System.out.println("Server connection closed");
        }*/
    }

    @Override
    public String getMessage() {
        return buffer.get(0);
    }

    @Override
    public void close() {
        //TODO
    }


    @Override
    public void send(String message) throws RemoteException {
        if(message.equals("ping"))
            sendMessage("pong");
        else {
            System.out.println(message);
            //buffer.add(message);
        }
    }
}
