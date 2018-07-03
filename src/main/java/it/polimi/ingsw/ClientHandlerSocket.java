package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.observer.Observable;

public class ClientHandlerSocket implements ClientHandler {

    private ClientSocketInterpreter clientSocketInterpreter;
    private Gson gson;
    private SocketVisitor socketVisitor;

    public ClientHandlerSocket(ClientSocketInterpreter clientSocketInterpreter) {
        socketVisitor = new SocketVisitor();
        this.clientSocketInterpreter = clientSocketInterpreter;
        this.gson = new Gson();
    }

    @Override
    public void displayGame(Table table) throws NetworkErrorException {
        try{
            clientSocketInterpreter.sendMessage("game/displayGame");
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try{
            clientSocketInterpreter.sendMessage(message);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendActiveTableElement(String element) throws NetworkErrorException {
        try{
            clientSocketInterpreter.sendMessage("game/active_table_element/"+element);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendPatternCard(PatternCard patternCard) throws NetworkErrorException {
        String patternCardJSON = gson.toJson(patternCard);
        try{
            clientSocketInterpreter.sendMessage("game/pattern_card/"+patternCardJSON);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws NetworkErrorException {
        String privateObjectiveCardJSON = gson.toJson(privateObjectiveCard);
        try{
            clientSocketInterpreter.sendMessage("game/private_objective_card/"+privateObjectiveCardJSON);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    @Override
    public void update(Observable o, String message) throws NetworkErrorException {
        String observable = o.convert(socketVisitor);
        String protocolMessage = "game/update/"+observable;
        if (message != null)
                protocolMessage = protocolMessage.concat("/"+message);
        try{
            clientSocketInterpreter.sendMessage(protocolMessage);
        } catch (Exception e){
            throw new NetworkErrorException();
        }
    }

    @Override
    public void check() throws NetworkErrorException {
        if(!clientSocketInterpreter.isOnline())
            throw new NetworkErrorException();
    }
}
