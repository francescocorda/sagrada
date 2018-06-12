package it.polimi.ingsw.view;

import it.polimi.ingsw.ClientHandler;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.ClientData;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.util.Observable;

public class VirtualView extends Observable implements View {

    private ClientHandler clientHandler;
    private ClientData clientData;

    public VirtualView(ClientData clientData) {
        super();
        this.clientData = clientData;
        this.clientHandler = clientData.getClientHandler();
    }

    public String getUsername() {
        return clientData.getUsername();
    }

    @Override
    public void displayGame() {
        try {
            clientHandler.displayGame();
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayMessage(String message) {
        try {
            clientHandler.sendMessage(message);
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayGameMessage(String message) {
        try {
            clientHandler.sendGameMessage(message);
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {

    }

    @Override
    public void displayPrivateObjectiveCard() {

    }

    @Override
    public void displayPatternCard(PatternCard patternCard) {
        try {
            clientHandler.sendPatternCard(patternCard);
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            clientHandler.update(o, arg);
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}