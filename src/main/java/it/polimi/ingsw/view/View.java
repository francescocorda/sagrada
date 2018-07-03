package it.polimi.ingsw.view;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.observer.Observer;


public interface View extends Observer {
    void displayGame(Table table);
    void displayMessage(String message);
    void activeTableElement(String element);
    void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard);
    void displayPatternCard(PatternCard patternCard);
    String getUsername();
}
