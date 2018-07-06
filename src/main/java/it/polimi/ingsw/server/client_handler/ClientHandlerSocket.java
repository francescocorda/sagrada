package it.polimi.ingsw.server.client_handler;

import com.google.gson.Gson;
import it.polimi.ingsw.server.socket.ClientSocketInterpreter;
import it.polimi.ingsw.server.socket.SocketVisitor;
import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.observer.Observable;

public class ClientHandlerSocket implements ClientHandler {

    private static final String DISPLAY_GAME = "game/displayGame";
    private static final String ACTIVE_ELEMENT = "game/active_table_element/";
    private static final String PATTERN_CARD = "game/pattern_card/";
    private static final String PRIVATE_OBJECTIVE_CARD = "game/private_objective_card/";
    private static final String UPDATE = "game/update/";
    private ClientSocketInterpreter clientSocketInterpreter;
    private Gson gson;
    private SocketVisitor socketVisitor;

    /**
     * creates a new {@link ClientHandlerSocket} given the {@link ClientSocketInterpreter} clientSocketInterpreter
     * @param clientSocketInterpreter : the given {@link ClientSocketInterpreter}
     */
    public ClientHandlerSocket(ClientSocketInterpreter clientSocketInterpreter) {
        socketVisitor = new SocketVisitor();
        this.clientSocketInterpreter = clientSocketInterpreter;
        this.gson = new Gson();
    }

    /**
     *displays to the client the given {@link Table} table.
     * it does that by sending {@link #DISPLAY_GAME} through method
     * {@link ClientSocketInterpreter#sendMessage(String)}
     * @param table : the given {@link Table}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void displayGame(Table table) throws NetworkErrorException {
        try{
            clientSocketInterpreter.sendMessage(DISPLAY_GAME);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    /**
     * sends the given {@link String} message.
     * it does that by sending the given {@link String} message through method
     * {@link ClientSocketInterpreter#sendMessage(String)}
     * @param message : the given {@link String}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try{
            clientSocketInterpreter.sendMessage(message);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    /**
     * sends to the client the given {@link String} element that represents the active element.
     * it does that by sending {@link #ACTIVE_ELEMENT} + the given {@link String} element through method
     * {@link ClientSocketInterpreter#sendMessage(String)}
     * @param element : the given {@link String}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void sendActiveTableElement(String element) throws NetworkErrorException {
        try{
            clientSocketInterpreter.sendMessage(ACTIVE_ELEMENT+element);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    /**
     * sends to the client the given {@link PatternCard} patternCard.
     * it does that by sending {@link #PATTERN_CARD} + {@link String} patternCardJSON of
     * the given {@link PatternCard} patternCard through method
     * {@link ClientSocketInterpreter#sendMessage(String)}
     * @param patternCard : the given {@link PatternCard}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void sendPatternCard(PatternCard patternCard) throws NetworkErrorException {
        String patternCardJSON = gson.toJson(patternCard);
        try{
            clientSocketInterpreter.sendMessage(PATTERN_CARD+patternCardJSON);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    /**
     * sends to the client the given {@link PrivateObjectiveCard} privateObjectiveCard.
     * it does that by sending {@link #PRIVATE_OBJECTIVE_CARD} + {@link String} privateObjectiveCardJSON of
     * the given {@link PrivateObjectiveCard} privateObjectiveCard through method
     * {@link ClientSocketInterpreter#sendMessage(String)}
     * @param privateObjectiveCard : the given {@link PrivateObjectiveCard}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws NetworkErrorException {
        String privateObjectiveCardJSON = gson.toJson(privateObjectiveCard);
        try{
            clientSocketInterpreter.sendMessage(PRIVATE_OBJECTIVE_CARD+privateObjectiveCardJSON);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    /**
     * updates the client of the given {@link Observable} o.
     * it does that by sending {@link #UPDATE} + {@link String} observable of
     * the given {@link Observable} o through method
     * {@link ClientSocketInterpreter#sendMessage(String)}
     * @param o : the given {@link Observable}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void update(Observable o) throws NetworkErrorException {
        String observable = o.convert(socketVisitor);
        String protocolMessage = UPDATE+observable;
        try{
            clientSocketInterpreter.sendMessage(protocolMessage);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }


    /**
     * checks if the client is still online.
     * it does that by calling method {@link ClientSocketInterpreter#isOnline()}
     * @throws NetworkErrorException if any connection related exception is thrown
     */
    @Override
    public void check() throws NetworkErrorException {
        if(!clientSocketInterpreter.isOnline())
            throw new NetworkErrorException();
    }

    /**
     * closes this {@link ClientHandler}.
     * it does that by calling method {@link ClientSocketInterpreter#close()}
     */
    @Override
    public void close() {
        clientSocketInterpreter.close();
    }
}
