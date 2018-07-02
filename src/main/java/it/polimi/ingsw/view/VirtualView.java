package it.polimi.ingsw.view;

import it.polimi.ingsw.ClientDatabase;
import it.polimi.ingsw.ClientHandler;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.ClientData;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.SocketVisitor;


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
            System.out.println("2");
            disconnect();
        }
    }

    @Override
    public void displayMessage(String message) {
        try {
            getClientHandler().sendMessage(message);
        } catch (NetworkErrorException e) {
            System.out.println("3");
            disconnect();
        }
    }

    @Override
    public void displayGameMessage(String message) {
        try {
            getClientHandler().sendGameMessage(message);
        } catch (NetworkErrorException e) {
            System.out.println("4");
            disconnect();
        }
    }

    @Override
    public void activeTableElement(String element) {
        try {
            getClientHandler().sendActiveTableElement(element);
        } catch (NetworkErrorException e) {
            System.out.println("5");
            disconnect();
        }
    }

    @Override
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        try {
            getClientHandler().sendPrivateObjectiveCard(privateObjectiveCard);
        } catch (NetworkErrorException e) {
            System.out.println("6");
            disconnect();
        }
    }

    @Override
    public void displayPatternCard(PatternCard patternCard) {
        try {
            getClientHandler().sendPatternCard(patternCard);
        } catch (NetworkErrorException e) {
            System.out.println("7");
            disconnect();
        }
    }

    @Override
    public void update(Observable o, String message) {
        try {
            getClientHandler().update(o, message);
        } catch (NetworkErrorException e) {
            System.out.println("8");
            disconnect();
        }
    }


    private ClientHandler getClientHandler(){
        return clientData.getClientHandler();
    }

    private void disconnect(){
        System.out.println("9");
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