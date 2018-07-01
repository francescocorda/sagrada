package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.ScoreTrack;
import it.polimi.ingsw.Model.Game.Table;

public interface GUIManager {
    void editMessage(String message);
    void showPattern(PatternCard pattern);
    void updateTable(Table table);
    void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard);
    void showScoreTrack(ScoreTrack scoreTrack);
    void activeElement(String element);
}
