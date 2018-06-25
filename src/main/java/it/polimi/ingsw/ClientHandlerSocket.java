package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.util.Observable;

public class ClientHandlerSocket implements ClientHandler {

    private ClientSocketInterpreter clientSocketInterpreter;
    private Gson gson;

    public ClientHandlerSocket(ClientSocketInterpreter clientSocketInterpreter) {
        this.clientSocketInterpreter = clientSocketInterpreter;
        this.gson = new Gson();
    }

    @Override
    public void displayGame() throws NetworkErrorException {
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
    public void sendGameMessage(String message) throws NetworkErrorException {
        try{
            clientSocketInterpreter.sendMessage("game/message/"+message);
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
    public void update(Observable o, Object arg) throws NetworkErrorException {
        if(o instanceof Table){
            String observable = ((Table) o).toJson();
            String object = gson.toJson(arg);
            try{
                clientSocketInterpreter.sendMessage("game/update/"+observable+"/"+object);
            } catch (Exception e){
                throw new NetworkErrorException();
            }
        }
    }

    @Override
    public void check() throws NetworkErrorException {
        if(!clientSocketInterpreter.isOnline())
            throw new NetworkErrorException();
    }
}
