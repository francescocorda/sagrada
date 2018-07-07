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

    private transient ClientData clientData;

    /**
     * creates a new {@link VirtualView} from the given {@link ClientData}.
     * @param clientData : the given {@link ClientData}
     */
    public VirtualView(ClientData clientData) {
        super();
        this.clientData = clientData;
    }

    /**
     * @return a deep copy of the {@link VirtualView}.
     */
    @Override
    public VirtualView copy() {
        return new VirtualView(this.clientData);
    }

    /**
     * @return the {@link ClientData#username}
     */
    public String getUsername() {
        return clientData.getUsername();
    }

    /**
     * displays game's {@link Table}.
     * it does that through method {@link ClientHandler#displayGame(Table)}
     * @param table : {@link Table} to be displayed
     */
    @Override
    public void displayGame(Table table) {
        try {
            getClientHandler().displayGame(table);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    /**
     * displays a given {@link String} message.
     * it does that through method {@link ClientHandler#sendMessage(String)}
     * @param message : {@link String} to be displayed
     */
    @Override
    public void displayMessage(String message) {
        try {
            if(clientData.isConnected())
                getClientHandler().sendMessage(message);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    /**
     * shows which is the element to be activated.
     * it does that through method {@link ClientHandler#sendActiveTableElement(String)}
     * @param element : {@link String} that contains a reference to the element to be activated
     */
    @Override
    public void activeTableElement(String element) {
        try {
            getClientHandler().sendActiveTableElement(element);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    /**
     * displays a given {@link PrivateObjectiveCard}.
     * it does that through method {@link ClientHandler#sendPrivateObjectiveCard(PrivateObjectiveCard)}
     * @param privateObjectiveCard : {@link PrivateObjectiveCard} to be displayed
     */
    @Override
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        try {
            getClientHandler().sendPrivateObjectiveCard(privateObjectiveCard);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    /**
     * displays a given {@link PatternCard}.
     * it does that through method {@link ClientHandler#sendPatternCard(PatternCard)}
     * @param patternCard : {@link PatternCard} to be displayed
     */
    @Override
    public void displayPatternCard(PatternCard patternCard) {
        try {
            getClientHandler().sendPatternCard(patternCard);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    /**
     *
     * @param message : the given {@link String} message
     */
    @Override
    public void update(String message) {
        try {
            getClientHandler().sendMessage(message);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    /**
     * updates this {@link VirtualView} about the given {@link Observable} o.
     * @param o : the given {@link Observable}
     */
    @Override
    public void update(Observable o) {
        try {
            getClientHandler().update(o);
        } catch (NetworkErrorException e) {
            disconnect();
        }
    }

    /**
     * @return {@link ClientData#clientHandler}.
     */
    private ClientHandler getClientHandler(){
        return clientData.getClientHandler();
    }

    /**
     * disconnects player.
     */
    private void disconnect(){
        ClientDatabase.getPlayerDatabase().disconnect(clientData.getUsername());
    }

    @Override
    public void display(ViewVisitor visitor) {
        // does nothing
    }

    @Override
    public String convert(SocketVisitor visitor) {
        return null;
    }
}