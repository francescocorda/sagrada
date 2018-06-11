package it.polimi.ingsw.client;

import it.polimi.ingsw.ClientHandlerInterface;
import it.polimi.ingsw.client.GUI.GUIData;
import it.polimi.ingsw.client.RMI.RMIClientImplementation;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.View;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CommunicatorRMI implements Communicator {
    private View view;
    private RMIClientInterface client;
    private ClientHandlerInterface server;
    public CommunicatorRMI(View view) {
        this.view = view;
    }

    @Override
    public void initialize(ArrayList<String> parameters) throws NetworkErrorException{
        String address = parameters.remove(0);
        try {
            this.client = (RMIClientInterface) UnicastRemoteObject.exportObject(new RMIClientImplementation(view), 0);
            this.server = (ClientHandlerInterface) Naming.lookup("//" + address + "/ClientHandler");
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void login(String username, String password) throws NetworkErrorException, NotValidInputException {
        try {
            server.login(username, password, client);
        } catch (RemoteException | NullPointerException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void lobby(String username, Long time) throws NetworkErrorException, NotValidInputException {
        try {
            server.joinLobby(username, time);
        } catch (RemoteException | NullPointerException e) {
            throw new NetworkErrorException();
        } catch (NotValidInputException e) {
            throw new NotValidInputException();
        }
    }

    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try {
            server.update(view.getUsername()+"/"+message);
        } catch (RemoteException | NullPointerException e) {
            throw new NetworkErrorException();
        }
    }
}
