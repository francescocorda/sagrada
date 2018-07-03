package it.polimi.ingsw.client.RMI;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.view.CLIView;
import it.polimi.ingsw.view.View;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

public class RMIClientImplementation implements RMIClientInterface {

    private View view;

    /**
     * creates a new {@link RMIClientImplementation} given a {@link View}.
     * @param view is the given view
     */
    public RMIClientImplementation(View view) {
        this.view = view;
    }

    /**
     * is used by the server to send a message to it's client through RMI.
     * It gets a message from the server in witch all commands are separated on another by "/" and
     * create an {@link ArrayList<String>} of all the commands to be sent to {@link #lobby(ArrayList)},
     * omitting the first one, or display directly the message
     * through {@link View#displayGameMessage(String)}
     * @param message : is the given message
     * @throws RemoteException if there's any connection related problem
     */
    public void send(String message) throws RemoteException{
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
        if(commands.remove(0).equals("lobby"))
            lobby(commands);
        else
            view.displayGameMessage(message);
    }

    /**
     * handles lobby related commands
     * @param commands : commands to be handles
     */
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

    /**
     * return a well formatted string representing the list of player's names.
     * @param players :  {@link ArrayList<String>} containing player's names
     * @return a {@link String} which is the well formatted list of player's names
     */
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

    /**
     * returns the {@link #view}
     * @return {@link #view}
     */
    public View getView() {
        return view;
    }

    /**
     * sets the attribute {@link #view}
     * @param view is the {@link View} to be set
     */
    public void setView(CLIView view) {
        this.view = view;
    }

    /**
     * allows to send a {@link PrivateObjectiveCard} that once received is being displayed.
     * Once the method is invoked it display the given {@link PrivateObjectiveCard} through the use of
     * method {@link View#displayPrivateObjectiveCard(PrivateObjectiveCard)}
     * @param privateObjectiveCard : is the given {@link PrivateObjectiveCard}
     * @throws RemoteException if there's any connection related problem
     */
    @Override
    public void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws RemoteException {
        view.displayPrivateObjectiveCard(privateObjectiveCard);
    }

    /**
     * allows to send a {@link PatternCard} that once received is being displayed.
     * Once the method is invoked it display the given {@link PatternCard} through the use of
     * method {@link View#displayPatternCard(PatternCard)}
     * @param patternCard : is the given {@link PatternCard}
     * @throws RemoteException if there's any connection related problem
     */
    @Override
    public void sendPatternCard(PatternCard patternCard) throws RemoteException {
        view.displayPatternCard(patternCard);
    }

    /**
     * allows to send a {@link String} representing the active element.
     * Once the method is invoked it notify the client through method {@link View#activeTableElement(String)}
     * @param element : message representing the active element
     * @throws RemoteException if there's any connection related problem
     */
    @Override
    public void sendActiveTableElement(String element) throws RemoteException {
        view.activeTableElement(element);
    }

    /**
     * This method has only to check the connection between client and server.
     * @throws RemoteException if there's any connection related problem
     */
    public void checkConnection()  throws RemoteException{
        //This method has only to check the connection between client and server, so it has to be empty
    }

    /**
     * allows to updates client's {@link View} through it's method {@link View#update(Observable, String)}.
     * @param o : {@link Observable} object to be passed as first param of method {@link View#update(Observable, String)}
     * @param message : {@link String} object to be passed as second param of method
     *                method {@link View#update(Observable, String)}
     * @throws RemoteException if there's any connection related problem
     */
    public void update(Observable o, String message) throws RemoteException{
        view.update(o, message);
    }

    /**
     * allows to display the Game.
     * it does that by calling {@link View#displayGame(Table)} given the {@link Table} to be passed
     * @param table : {@link Table} to be passed
     * @throws RemoteException if there's any connection related problem
     */
    @Override
    public void displayGame(Table table) throws RemoteException {
        view.displayGame(table);
    }
}
