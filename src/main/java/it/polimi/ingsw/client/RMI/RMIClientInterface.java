package it.polimi.ingsw.client.RMI;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.observer.Observable;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    void send(String message) throws RemoteException;

    void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws RemoteException;

    void sendPatternCard(PatternCard patternCard) throws RemoteException;

    void sendActiveTableElement(String element) throws RemoteException;

    void checkConnection() throws RemoteException;

    void update(Observable o, String message) throws RemoteException;

    void displayGame(Table table) throws RemoteException;
}
