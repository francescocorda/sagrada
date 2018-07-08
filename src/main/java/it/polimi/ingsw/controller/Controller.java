package it.polimi.ingsw.controller;

import it.polimi.ingsw.database.ClientData;
import it.polimi.ingsw.database.ClientDatabase;
import it.polimi.ingsw.database.Phase;
import it.polimi.ingsw.database.VirtualViewsDataBase;
import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.database.ParserManager;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.VirtualView;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Observer {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    private static final String INACTIVE_TABLE = "INACTIVE_TABLE";
    private static final String JOIN_ACTION = "JOIN";
    private int turnTimerSeconds;
    static final String INVALID_FORMAT = "Command of invalid format.";
    static final String WAIT_YOUR_TURN = "Wait your turn.";
    static final String CHOOSE_TOOL_CARD = "Choose the tool card to use (1-2-3).";
    static final String CHOOSE_PATTERN_CARD = "Choose the pattern card to use (1-2-3-4).";
    private static final String PLAYER_NOT_FOUND = "Player not found.";
    private static final String ITS_YOUR_TURN = "It's your turn! Choose Action: move, toolcard, skip.";
    static final String ACTION_CANCELED = "Action canceled.";
    static final String LEFT_THE_GAME = " left the game.";
    static final String YOU_LEFT_THE_GAME = "You left the game. Choose join to get back.";
    static final String JOINED_THE_GAME = " joined the game.";
    static final String GAME_JOINED = "Game joined.";
    static final String BACK_TO_GAME = "back_to_game";
    static final String PATTERN_ASSIGNED = "Pattern card assigned.";
    private static final String YOU_WON = "You Won!";
    private static final String CHOOSE_ACTION = "CHOOSE_ACTION";
    private static final String START = "START";
    static final String GAME_END = "Game end.";
    static final String END_GAME_MESSAGE = "Choose [play] to play again, [logout] to go back to login";
    static final int CHOOSE_ACTION_DIM = 1;

    private State startState;
    private State chooseActionState;
    private State moveState;
    private State buyToolCardState;
    private State useToolCardState;
    private State endState;

    private State state;

    private List<VirtualView> views;
    private Timer timer;
    private ArrayList<String> players;
    private ArrayList<String> offlinePlayers;
    private Game game;

    private boolean isGameEnded;

    /**
     * creates a new {@link Controller} from the given {@link List<VirtualView>} of views and int matchID.
     * @param matchID : the given match ID.
     * @param views : the given {@link List<VirtualView>} of views.
     */
    public Controller(int matchID, List<VirtualView> views) {
        isGameEnded = false;
        turnTimerSeconds = Server.getServerMain().getTurnSeconds();
        offlinePlayers = new ArrayList<>();
        ParserManager pm = ParserManager.getParserManager();
        players = new ArrayList<>();
        for (VirtualView view : views) {
            players.add(view.getUsername());
        }
        game = new Game(matchID, players);
        game.setPatternDeck(pm.getPatternDeck());
        game.setPublicObjectiveDeck(pm.getPublicObjectiveDeck());
        game.setToolCards(pm.getToolCards());
        this.views = views;
        for (VirtualView virtualView : views) {
            game.addObserver(virtualView);
            virtualView.addObserver(this);
        }
        timer = new Timer();
        startState = new StartState(this);
        chooseActionState = new ChooseActionState(this);
        moveState = new MoveState(this);
        buyToolCardState = new BuyToolCardState(this);
        useToolCardState = new UseToolCardState(this);
        endState = new EndState(this);
        state = startState;
        startGame();
    }

    /**
     * @return an {@link ArrayList<String>} of all the players in the game.
     */
    public ArrayList<String> getPlayers() {
        return players;
    }

    /**
     * @return an {@link ArrayList<String>} of all the offline players in the game.
     */
    public ArrayList<String> getOfflinePlayers() {
        return offlinePlayers;
    }

    /**
     * @return a instance of the current {@link State} of the controller.
     */
    State getState() {
        return state;
    }

    /**
     * sets the current {@link State} of the Controller
     * @param state is the given {@link State}
     */
    void setState(State state) {
        this.state = state;
    }

    /**
     * @return a instance of the {@link StartState} of the controller.
     */
    State getStartState() {
        return startState;
    }

    /**
     * @return a instance of the {@link ChooseActionState} of the controller.
     */
    State getChooseActionState() {
        return chooseActionState;
    }

    /**
     * @return a instance of the {@link MoveState} of the controller.
     */
    State getMoveState() {
        return moveState;
    }

    /**
     * @return a instance of the {@link BuyToolCardState} of the controller.
     */
    State getBuyToolCardState() {
        return buyToolCardState;
    }

    /**
     * @return a instance of the {@link UseToolCardState} of the controller.
     */
    State getUseToolCardState() {
        return useToolCardState;
    }

    /**
     * @return a instance of the {@link EndState} of the controller.
     */
    State getEndState() {
        return endState;
    }

    /**
     * @param username is the given username to check
     * @return true if the game contains a player with the same name as the parameter, false otherwise
     */
    public boolean contains(String username) {
        return (offlinePlayers.contains(username));
    }

    /**
     * This method prepares the board table for the {@link Game}:
     * rolls the Draw Pool for the first time
     * draws and assigns 1 {@link it.polimi.ingsw.model.cards.public_objectives.PublicObjectiveCard} to each player
     * sends 4 {@link PatternCard} to each player from which he will choose one.
     * Then it starts a timer that assign 1 {@link PatternCard} to each player that hasn't chosen it yet.
     */
    private void startGame() {
        game.drawDices();
        game.drawPublicObjectiveCards();
        game.drawToolCards();
        for (VirtualView view : views) {
            PrivateObjectiveCard privateObjectiveCard;
            try {
                privateObjectiveCard = game.assignPrivateObjectiveCard(view.getUsername());
                ArrayList<PatternCard> patterns = game.drawPatternCards();
                view.displayPrivateObjectiveCard(privateObjectiveCard);
                for (PatternCard patternCard : patterns) {
                    view.displayPatternCard(patternCard);
                }
                view.displayMessage(CHOOSE_PATTERN_CARD);
            } catch (NotValidInputException e) {
                logger.log(Level.SEVERE, PLAYER_NOT_FOUND);
            }
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (String name : players) {
                    if (game.setPatternCard(name, 0)) {
                        sendActiveTableElement(name, START);
                        sendMessage(name, PATTERN_ASSIGNED);
                    }
                }
                game.doneAssignPatternCards();
                state = chooseActionState;
                sendActiveTableElement(game.getCurrentPlayer(), CHOOSE_ACTION);
                itsYourTurn();
                setTimerSkipTurn();
            }
        }, turnTimerSeconds * 1000);
    }

    /**
     * Sends a message to the {@link VirtualView} associated with the given name.
     * @param name is the player's name
     * @param message is the message to send
     */
    public void sendMessage(String name, String message) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(name)) {
                virtualView.displayMessage(message);
            }
        }
    }

    /**
     * Updates the table view of the selected player's {@link VirtualView}.
     * @param name is the given {@link String} name
     */
    public void updateTable(String name) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(name) && !game.isGameEnded()) {
                virtualView.displayGame(game.getTableCopy());
            }
        }
    }

    /**
     * Send the current active table element to the selected player's {@link VirtualView}.
     * @param name is the given {@link String} name
     */
    public void sendActiveTableElement(String name) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(name)) {
                virtualView.activeTableElement(game.getActiveTableElement());
            }
        }
    }

    /**
     * Send the current active table element to the selected player's {@link VirtualView}.
     * @param name is the given {@link String} name
     * @param element is the given {@link String} active element
     */
    public void sendActiveTableElement(String name, String element) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(name)) {
                virtualView.activeTableElement(element);
            }
        }
    }

    /**
     * * updates this {@link Controller} with the given {@link String} message.
     * @param message : the given {@link String} message
     */
    @Override
    public synchronized void update(String message) {
        ArrayList<String> commands;
        commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
        handleEvent(commands);
    }

    /**
     * * updates this {@link Controller} with the given {@link Observable} o.
     * @param o : the given {@link String} message
     */
    @Override
    public synchronized void update(Observable o) {
    }

    /**
     * handles Game Events:
     * disconnects the player if the received command is "logout",
     * reconnects the players if the received command is "join",
     * delegates the interpretation of the commands to the current state in the other cases.
     * @param commands is the given {@link ArrayList<String>} of received commands
     */
    private synchronized void handleEvent(ArrayList<String> commands) {
        if (commands.size() > 1) {
            String username = commands.remove(0);
            if (players.contains(username)) {
                if (!offlinePlayers.contains(username) && commands.get(0).equals("logout") && commands.size() == 1) {
                    ClientDatabase.getPlayerDatabase().disconnect(username); //TODO commentare per tornare alla situazione di prima
                    state.exitGame(username);
                } else if (!isGameEnded && offlinePlayers.contains(username) && commands.get(0).equals("join") && commands.size() == 1) {
                    state.joinGame(username);
                }/* else if (!offlinePlayers.contains(username) && commands.get(0).equals("logout") && commands.size() == 1) {
                    ClientDatabase.getPlayerDatabase().disconnect(username);
                } */else if (!offlinePlayers.contains(username) || isGameEnded) {
                    state.handleEvent(username, commands);
                } else {
                    System.out.println(commands.get(0));
                }
            }
        }
    }

    /**
     * deletes the VirtualView observer associated with the given username from the Game observers list
     */
    void deleteObserver(String username) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(username)) {
                game.deleteObserver(virtualView);
                return;
            }
        }
    }

    /**
     * adds the VirtualView observer associated with the given username to the Game observers list
     */
    void addObserver(String username) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(username)) {
                game.addObserver(virtualView);
                return;
            }
        }
    }

    /**
     * Skips the current turn if the game is not ended, calls a check of the game current state.
     */
    void skipTurn() {
        if (!game.isGameEnded())
            game.skipTurn();
        checkGameState();
    }

    /**
     * sends to the current player the message indicating that it's their turn
     */
    void itsYourTurn() {
        sendMessage(game.getCurrentPlayer(), ITS_YOUR_TURN);
    }

    /**
     * Checks the current state of the game, acting differently if the turn, round or game is ended
     */
    synchronized void checkGameState() {
        String player = game.getCurrentPlayer();
        if (game.isTurnEnded()) {
            if (game.isRoundEnded() && game.isGameEnded()) {
                game.countScores();
                sendMessage(game.getWinner(), YOU_WON);
                state = endState;
                timer.cancel();
                endGame();
                game.deleteObservers();
                return;
            }
            if(!offlinePlayers.contains(player)) {
                sendActiveTableElement(player, INACTIVE_TABLE);
            }
            if (!offlinePlayers.contains(game.getCurrentPlayer())) {
                setTimerSkipTurn();
            }
        }

        if (!game.isGameEnded() && offlinePlayers.contains(game.getCurrentPlayer())) {
            skipTurn();
        } else if (offlinePlayers.size() == players.size() - 1) {
            if (!game.isGameEnded()) {
                state = endState;
                String lastPlayer = game.getCurrentPlayer();
                game.endGame();
                sendMessage(lastPlayer, YOU_WON);
                endGame();
                game.deleteObservers();
            }
            state = endState;
            timer.cancel();
        } else {
            state = chooseActionState;
            sendActiveTableElement(game.getCurrentPlayer(), CHOOSE_ACTION);
            itsYourTurn();
        }
    }

    /**
     * Sets the timer for the player's turn. Ends the turn if the player doesn't complete his moves in the time
     * assigned.
     */
    void setTimerSkipTurn() {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = chooseActionState;
                if (!offlinePlayers.contains(game.getCurrentPlayer())) {
                    offlinePlayers.add(game.getCurrentPlayer());
                    deleteObserver(game.getCurrentPlayer());
                    sendMessage(game.getCurrentPlayer(), YOU_LEFT_THE_GAME);
                    sendActiveTableElement(game.getCurrentPlayer(), JOIN_ACTION);
                }
                skipTurn();
            }
        }, turnTimerSeconds * 1000);
    }

    /**
     * Handles the offline players and the online players properly when the game ends
     */
    private void endGame() {
        handleOfflinePlayers();
        isGameEnded = true;
        handleOnlinePlayers();
    }

    /**
     * Sends to the online players a message asking if the want to play again
     */
    void handleOnlinePlayers() {
        for (String player : players) {
            if (!offlinePlayers.contains(player)) {
                sendMessage(player, END_GAME_MESSAGE);
            }
        }
    }

    /**
     * Removes the controller from the Lobby, deletes the VirtualViews observers
     * and kills the offline players Virtual View
     */
    void handleOfflinePlayers() {
        Lobby.getLobby().removeController(this);
        for (String offlinePlayer : offlinePlayers) {
            handleOfflinePlayer(offlinePlayer);
        }
    }

    /**
     * deletes the VirtualView's observers and remove the Virtual View from the database
     * @param username is the name associated with the Virtual View
     */
    void handleOfflinePlayer(String username){
        VirtualViewsDataBase virtualViewsDataBase = VirtualViewsDataBase.getVirtualViewsDataBase();
        ClientData client = ClientDatabase.getPlayerDatabase().getPlayerData(username);
        if (!client.isConnected()) {
            virtualViewsDataBase.getVirtualView(username).deleteObservers();
            virtualViewsDataBase.removeVirtualView(username);
            client.setPhase(Phase.LOGIN);
        }
    }

    /**
     * @return an instance of the Game
     */
    Game getGame() {
        return game;
    }

    /**
     * @return an instance of {@link List<VirtualView>}
     */
    List<VirtualView> getViews() {
        return views;
    }
}
