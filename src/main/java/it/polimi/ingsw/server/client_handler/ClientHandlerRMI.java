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

    /**
     * creates a {@link ClientHandlerRMI} given a {@link RMIClientInterface} rmiClientInterface
     * @param rmiClientInterface : the given {@link RMIClientInterface}
     */
    public ClientHandlerRMI(RMIClientInterface rmiClientInterface) {
        this.rmiClientInterface = rmiClientInterface;
    }

    /**
     * displays to the client the given {@link Table} table.
     * @param table : the given {@link Table}
     * @throws NetworkErrorException
     */
    @Override
    public void displayGame(Table table) throws NetworkErrorException {
        try {
            rmiClientInterface.displayGame(table);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * sends the given {@link String} message.
     * it does that through method {@link RMIClientInterface#send(String)}
     * @param message : the given {@link String}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try {
            rmiClientInterface.send(message);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * sends to the client the given {@link String} element that represents the active element.
     * it does that through method {@link RMIClientInterface#sendActiveTableElement(String)}
     * @param element : the given {@link String}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void sendActiveTableElement(String element) throws NetworkErrorException {
        try {
            rmiClientInterface.sendActiveTableElement(element);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * sends to the client the given {@link PatternCard} patternCard.
     * it does that through method {@link RMIClientInterface#sendActiveTableElement(String)}
     * @param patternCard : the given {@link PatternCard}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void sendPatternCard(PatternCard patternCard) throws NetworkErrorException {
        try {
            rmiClientInterface.sendPatternCard(patternCard);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * sends to the client the given {@link PrivateObjectiveCard} privateObjectiveCard.
     * it does that through method {@link RMIClientInterface#sendPrivateObjectiveCard(PrivateObjectiveCard)}
     * @param privateObjectiveCard : the given {@link PrivateObjectiveCard}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws NetworkErrorException {
        try {
            rmiClientInterface.sendPrivateObjectiveCard(privateObjectiveCard);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * updates the client of the given {@link Observable} o.
     * it does that through method {@link RMIClientInterface#update(Observable)}
     * @param o : the given {@link Observable}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void update(Observable o) throws NetworkErrorException {
        try {
            rmiClientInterface.update(o);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * checks if the client is still online.
     * it does that through method {@link RMIClientInterface#checkConnection()}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public synchronized void check() throws NetworkErrorException {
        try {
            rmiClientInterface.checkConnection();
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * does nothing.
     */
    @Override
    public void close() {
        //useless for rmi
    }
}
