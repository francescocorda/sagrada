package it.polimi.ingsw.view;

import it.polimi.ingsw.database.ClientDatabase;
import it.polimi.ingsw.server.client_handler.ClientHandler;
import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.database.ClientData;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.socket.SocketVisitor;


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
    public void displayGame(Table table) {
        try {
            getClientHandler().displayGame(table);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    @Override
    public void displayMessage(String message) {
        try {
            if(clientData.isConnected())
                getClientHandler().sendMessage(message);
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
    public void update(String message) {
        try {
            getClientHandler().sendMessage(message);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    @Override
    public void update(Observable o) {
        try {
            getClientHandler().update(o);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    private ClientHandler getClientHandler(){
        return clientData.getClientHandler();
    }

    private void disconnect(){
        ClientDatabase.getPlayerDatabase().disconnect(clientData.getUsername());
    }

    @Override
    public void display(ViewVisitor visitor) {

    }

    @Override
    public String convert(SocketVisitor visitor) {
        return null;
    }
}