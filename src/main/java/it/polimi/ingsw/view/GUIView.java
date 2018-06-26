package it.polimi.ingsw.view;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.client.GUI.GUIManager;

import java.util.Observable;

public class GUIView extends Observable implements View  {
    private GUIManager manager;
    private Table table;
    private String username;
    @Override
    public void displayGame() {
        manager.updateTable(table);
    }

    @Override
    public void displayMessage(String message) {
        manager.editMessage(message);
    }

    @Override
    public void displayGameMessage(String message) {
        manager.editMessage(message);
    }

    @Override
    public void activeTableElement(String element) {
        //TODO
        manager.editMessage(element);
    }

    @Override
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        manager.displayPrivateObjectiveCard(privateObjectiveCard);
    }

    @Override
    public void displayPatternCard(PatternCard patternCard) {
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
    public void update(Observable o, Object arg) {
        if(o instanceof Table) {
            Table table = (Table) o;
            this.table = table;
            if(arg == null) {
                displayGame();
            }
            else if(arg instanceof String) {
                String message = (String) arg;
                manager.editMessage(message);
            }
        }
    }
    public void setGUIManager(GUIManager manager){
        this.manager = manager;
    }
    public GUIManager getGUIManager(){
        return this.manager;
    }

}
