package it.polimi.ingsw.view;

import it.polimi.ingsw.ClientDatabase;
import it.polimi.ingsw.ClientHandler;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.ClientData;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.util.Observable;

public class VirtualView extends Observable implements View {

    private ClientData clientData;

    public VirtualView(ClientData clientData) {
        super();
        this.clientData = clientData;
    }

    public String getUsername() {
        return clientData.getUsername();
    }

    @Override
    public void displayGame() {
        try {
            getClientHandler().displayGame();
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    @Override
    public void displayMessage(String message) {
        try {
            getClientHandler().sendMessage(message);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    @Override
    public void displayGameMessage(String message) {
        try {
            getClientHandler().sendGameMessage(message);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    @Override
    public void activeTableElement(String element) {
        try {
            getClientHandler().sendActiveTableElement(element);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    @Override
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        try {
            getClientHandler().sendPrivateObjectiveCard(privateObjectiveCard);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    @Override
    public void displayPatternCard(PatternCard patternCard) {
        try {
            getClientHandler().sendPatternCard(patternCard);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            getClientHandler().update(o, arg);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

    private ClientHandler getClientHandler(){
        return clientData.getClientHandler();
    }

    private void disconnect(){
        ClientDatabase.getPlayerDatabase().disconnect(clientData.getUsername());
    }
}