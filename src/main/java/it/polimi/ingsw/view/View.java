package it.polimi.ingsw.view;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;

import java.util.Observer;

public interface View extends Observer {
    void displayGame();
    void displayMessage(String message);
    void displayGameMessage(String message);
    void activeTableElement(String element);
    void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard);
    void displayPatternCard(PatternCard patternCard);
    void notifyObservers(Object arg);
    String getUsername();
}
