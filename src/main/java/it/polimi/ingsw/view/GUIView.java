package it.polimi.ingsw.view;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.client.GUI.GUIManager;

import java.util.Observable;

public class GUIView extends Observable implements View  {
    private GUIManager manager;
    private Table table;
    @Override
    public void displayGame() {

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
    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {

    }

    @Override
    public void displayPrivateObjectiveCard() {

    }

    @Override
    public void displayPatternCard(PatternCard patternCard) {
        manager.showPattern(patternCard.getID());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Table) {
            Table table = (Table) o;
            this.table = table;
            if(arg == null) {
                manager.updateTable(table);
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
