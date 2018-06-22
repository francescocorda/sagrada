package it.polimi.ingsw.client.RMI;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.view.CLIView;
import it.polimi.ingsw.view.View;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class RMIClientImplementation implements RMIClientInterface {

    View view;

    public RMIClientImplementation(View view) {
        this.view = view;
    }

    public void send(String message) throws RemoteException{
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
        if(commands.remove(0).equals("lobby"))
            lobby(commands);
        else
            view.displayGameMessage(message);
    }

    private void lobby(ArrayList<String> commands){
        String message = commands.remove(0);
        switch (message){
            case "welcome":
                view.displayMessage("Welcome!");
                break;
            case "player_joined":
                view.displayMessage("Player joined: "+commands.remove(0));
                break;
            case "list_of_players":
                view.displayMessage("LIST OF PLAYERS:\n"+getListOfPlayers(commands));
                break;
            case "player_left":
                view.displayMessage("Player left: "+commands.remove(0));
                break;
            case "start_game":
                view.displayMessage("GAME START:");
                lobby(commands);
                break;
            case "timer_started":
                view.displayMessage("TIMER START!");
                break;
            case "timer_reset":
                view.displayMessage("TIMER RESET");
                break;
            default:
                view.displayMessage(message);
                break;
        }
    }

    private String getListOfPlayers(ArrayList<String> players){
        String enclosureSymbol = "-";
        String separatorSymbol = "| ";
        String message = separatorSymbol;
        for(String player : players){
            message = message.concat(player+" "+separatorSymbol);
        }
        String enclosure = new String();
        for(int i =1; i<message.length(); i++)
            enclosure = enclosure.concat(enclosureSymbol);
        message = enclosure +"\n"+message+"\n"+enclosure;
        return message;
    }

    public View getView() {
        return view;
    }

    public void setView(CLIView view) {
        this.view = view;
    }

    @Override
    public void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws RemoteException {
        view.displayPrivateObjectiveCard(privateObjectiveCard);
    }

    @Override
    public void sendPatternCard(PatternCard patternCard) throws RemoteException {
        view.displayPatternCard(patternCard);
    }


    public void checkConnection()  throws RemoteException{
        //This method has only to check the connection between client and server, so it has to be empty
    }

    public void update(Observable o, Object arg) throws RemoteException{
        view.update(o,arg);
    }

    @Override
    public void displayGame() throws RemoteException {
        view.displayGame();
    }

}
