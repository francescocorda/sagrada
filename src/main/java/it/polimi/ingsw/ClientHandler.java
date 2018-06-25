package it.polimi.ingsw;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.util.Observable;

public interface ClientHandler {
    void displayGame() throws NetworkErrorException;
    void sendMessage(String message) throws NetworkErrorException;
    void sendGameMessage(String message) throws NetworkErrorException;
    void sendActiveTableElement(String element) throws NetworkErrorException;
    void sendPatternCard(PatternCard patternCard) throws NetworkErrorException;
    void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws NetworkErrorException;
    void update(Observable o, Object arg) throws NetworkErrorException;
    void check() throws NetworkErrorException;
}
