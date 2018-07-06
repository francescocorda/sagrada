package it.polimi.ingsw.client.connection_mode.rmi;
import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.observer.Observable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    /**
     * is used by the server to send a message to it's client through rmi.
     * @param message : is the given message
     * @throws RemoteException if there's any connection related problem
     */
    void send(String message) throws RemoteException;

    /**
     * allows to send a {@link PrivateObjectiveCard} that once received is being displayed.
     * @param privateObjectiveCard : is the given {@link PrivateObjectiveCard}
     * @throws RemoteException if there's any connection related problem
     */
    void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws RemoteException;

    /**
     * allows to send a {@link PatternCard} that once received is being displayed.
     * @param patternCard : is the given {@link PatternCard}
     * @throws RemoteException if there's any connection related problem
     */
    void sendPatternCard(PatternCard patternCard) throws RemoteException;

    /**
     * allows to send a {@link String} representing the active element.
     * @param element : message representing the active element
     * @throws RemoteException if there's any connection related problem
     */
    void sendActiveTableElement(String element) throws RemoteException;

    /**
     * This method has only to check the connection between client and server.
     * @throws RemoteException if there's any connection related problem
     */
    void checkConnection() throws RemoteException;

    /**
     * allows to updates client's view.
     * @param o : {@link Observable} to be passed
     * @throws RemoteException if there's any connection related problem
     */
    void update(Observable o) throws RemoteException;

    /**
     * allows to display the game.
     * @param table : {@link Table} to be passed
     * @throws RemoteException if there's any connection related problem
     */
    void displayGame(Table table) throws RemoteException;
}
