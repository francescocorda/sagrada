package it.polimi.ingsw.client.RMI;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

public interface RMIClientInterface extends Remote {

    void send(String message) throws RemoteException;

    void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws RemoteException;

    void sendPatternCard(PatternCard patternCard) throws RemoteException;

    void checkConnection() throws RemoteException;

    void update(Observable o, Object arg) throws RemoteException;

    void displayGame() throws RemoteException;
}
