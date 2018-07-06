package it.polimi.ingsw.client.game_mode.gui;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.ScoreTrack;
import it.polimi.ingsw.model.game.Table;

public interface GUIManager {
    void editMessage(String message);
    void showPattern(PatternCard pattern);
    void updateTable(Table table);
    void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard);
    void showScoreTrack(ScoreTrack scoreTrack);
    void activeElement(String element);
}
