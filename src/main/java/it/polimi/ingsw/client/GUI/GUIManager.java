package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;

public interface GUIManager {
    public void editMessage(String message);
    public void showPattern(PatternCard pattern);
    public void updateTable(Table table);
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard);
}
