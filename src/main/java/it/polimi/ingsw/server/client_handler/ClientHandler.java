package it.polimi.ingsw.server.client_handler;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.observer.Observable;

public interface ClientHandler {
    /**
     *displays to the client the given {@link Table} table.
     * @param table : the given {@link Table}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    void displayGame(Table table) throws NetworkErrorException;

    /**
     * sends the given {@link String} message.
     * @param message : the given {@link String}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    void sendMessage(String message) throws NetworkErrorException;

    /**
     * sends to the client the given {@link String} element that represents the active element.
     * @param element : the given {@link String}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    void sendActiveTableElement(String element) throws NetworkErrorException;

    /**
     * sends to the client the given {@link PatternCard} patternCard.
     * @param patternCard : the given {@link PatternCard}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    void sendPatternCard(PatternCard patternCard) throws NetworkErrorException;

    /**
     * sends to the client the given {@link PrivateObjectiveCard} privateObjectiveCard.
     * @param privateObjectiveCard : the given {@link PrivateObjectiveCard}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws NetworkErrorException;

    /**
     * updates the client of the given {@link Observable} o.
     * @param o : the given {@link Observable}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    void update(Observable o) throws NetworkErrorException;

    /**
     * checks if the client is still online.
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    void check() throws NetworkErrorException;

    /**
     * closes this {@link ClientHandler}.
     */
    void close();
}
