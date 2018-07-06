package it.polimi.ingsw.server.client_handler;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.client.connection_mode.rmi.RMIClientInterface;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.observer.Observable;

import java.rmi.RemoteException;

public class ClientHandlerRMI implements ClientHandler {
    private RMIClientInterface rmiClientInterface;

    public ClientHandlerRMI(RMIClientInterface rmiClientInterface) {
        this.rmiClientInterface = rmiClientInterface;
    }

    @Override
    public void displayGame(Table table) throws NetworkErrorException {
        try {
            rmiClientInterface.displayGame(table);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try {
            rmiClientInterface.send(message);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendActiveTableElement(String element) throws NetworkErrorException {
        try {
            rmiClientInterface.sendActiveTableElement(element);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendPatternCard(PatternCard patternCard) throws NetworkErrorException {
        try {
            rmiClientInterface.sendPatternCard(patternCard);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws NetworkErrorException {
        try {
            rmiClientInterface.sendPrivateObjectiveCard(privateObjectiveCard);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void update(Observable o) throws NetworkErrorException {
        try {
            rmiClientInterface.update(o);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public synchronized void check() throws NetworkErrorException {
        try {
            rmiClientInterface.checkConnection();
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void close() {
        //useless for rmi
    }
}
