package it.polimi.ingsw;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.util.Observable;

public interface ClientHandler {
    public void displayGame() throws NetworkErrorException;
    public void sendMessage(String message) throws NetworkErrorException;
    public void sendGameMessage(String message) throws NetworkErrorException;
    public void sendPatternCard(PatternCard patternCard) throws NetworkErrorException;
    public void update(Observable o, Object arg) throws NetworkErrorException;
    public void check() throws NetworkErrorException;
    public void game();
}
