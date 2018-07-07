package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.client.game_mode.gui.GUIManager;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.socket.SocketVisitor;


public class GUIView extends Observable implements View {
    private transient GUIManager manager;
    private String username;
    private ViewVisitor visitor;
    private final Object sync = new Object();

    public GUIView() {
        visitor = new ViewVisitor(this);
        manager = null;
        username = null;
    }

    /**
     * @return a deep copy of the {@link GUIView}.
     */
    @Override
    public GUIView copy() {
        GUIView tempView = new GUIView();
        tempView.setUsername(this.username);
        return tempView;
    }

    @Override
    public void displayGame(Table table) {
        synchronized (sync) {
            manager.updateTable(table);
            sync.notifyAll();
        }
    }

    @Override
    public synchronized void displayMessage(String message) {
        manager.editMessage(message);
    }

    @Override
    public void activeTableElement(String element) {
        manager.activeElement(element);
    }

    @Override
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        manager.displayPrivateObjectiveCard(privateObjectiveCard);
    }

    @Override
    public synchronized void displayPatternCard(PatternCard patternCard) {
        manager.showPattern(patternCard);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void update(String message) {
        synchronized (sync) {
            if (message != null) {
                displayMessage(message);
            }
            sync.notifyAll();
        }
    }

    @Override
    public void update(Observable o) {
        o.display(visitor);
    }

    public void setGUIManager(GUIManager manager) {
        this.manager = manager;
    }

    public GUIManager getGUIManager() {
        return this.manager;
    }

    @Override
    public void display(ViewVisitor visitor) {

    }

    @Override
    public String convert(SocketVisitor visitor) {
        return null;
    }
}
