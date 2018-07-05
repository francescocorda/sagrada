package it.polimi.ingsw.view;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.observer.Observer;


public interface View extends Observer {
    /**
     * displays game's {@link Table}.
     * @param table : {@link Table} to be displayed
     */
    void displayGame(Table table);

    /**
     * displays a given message.
     * @param message : {@link String} to be displayed
     */
    void displayMessage(String message);

    /**
     * shows which is the element to be activated.
     * @param element : {@link String} that contains a reference to the element to be activated
     */
    void activeTableElement(String element);

    /**
     * displays a given {@link PrivateObjectiveCard}.
     * @param privateObjectiveCard : {@link PrivateObjectiveCard} to be displayed
     */
    void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard);

    /**
     * displays a given {@link PatternCard}.
     * @param patternCard : {@link PatternCard} to be displayed
     */
    void displayPatternCard(PatternCard patternCard);

    /**
     * @return player's {@link String} username.
     */
    String getUsername();
}
