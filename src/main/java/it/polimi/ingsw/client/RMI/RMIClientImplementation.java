package it.polimi.ingsw.client.RMI;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.view.CLIView;
import it.polimi.ingsw.view.View;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

public class RMIClientImplementation implements RMIClientInterface {

    View view;

    public RMIClientImplementation(View view) {
        this.view = view;
    }

    public void send(String message) throws RemoteException{
        view.displayMessage(message);
    }

    public View getView() {
        return view;
    }

    public void setView(CLIView view) {
        this.view = view;
    }

    @Override
    public void assignPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws RemoteException {
        view.setPrivateObjectiveCard(privateObjectiveCard);
    }

    @Override
    public void sendPatternCard(PatternCard patternCard) throws RemoteException {
        view.displayPatternCard(patternCard);
    }


    public void checkConnection()  throws RemoteException{
        //This method has only to check the connection between client and server, so it has to be empty
    }

    public void update(Observable o, Object arg) throws RemoteException{
        view.update(o,arg);
    }

    @Override
    public void displayGame() throws RemoteException {
        view.displayGame();
    }

}
