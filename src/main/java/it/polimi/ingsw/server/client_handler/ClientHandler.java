package it.polimi.ingsw.server.client_handler;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.observer.Observable;

public interface ClientHandler {
    void displayGame(Table table) throws NetworkErrorException;
    void sendMessage(String message) throws NetworkErrorException;
    void sendActiveTableElement(String element) throws NetworkErrorException;
    void sendPatternCard(PatternCard patternCard) throws NetworkErrorException;
    void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws NetworkErrorException;
    void update(Observable o) throws NetworkErrorException;
    void check() throws NetworkErrorException;
    void close();
}
