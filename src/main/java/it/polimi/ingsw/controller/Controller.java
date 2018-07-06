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
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.VirtualView;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Observer {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    private static String INACTIVE_TABLE = "INACTIVE_TABLE";
    private static String JOIN_ACTION = "JOIN";
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
    static final String GAME_JOINED = "game joined.";
    static final String BACK_TO_GAME = "back_to_game";
    static final String PATTERN_ASSIGNED = "Pattern card assigned.";
    private static final String YOU_WON = "You Won!";
    private static final String CHOOSE_ACTION = "CHOOSE_ACTION";
    private static final String START = "START";
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

    public ArrayList<String> getPlayers() {
        //TODO eliminate
        return players;
    }

    public ArrayList<String> getOFFPlayer() {
        //TODO eliminate
        return offlinePlayers;
    }

    public Controller(int matchID, List<VirtualView> views) {
        isGameEnded = false;
        turnTimerSeconds = ServerMain.getServerMain().getTurnSeconds();
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

    State getState() {
        return state;
    }

    void setState(State state) {
        this.state = state;
    }

    State getStartState() {
        return startState;
    }

    State getChooseActionState() {
        return chooseActionState;
    }

    State getMoveState() {
        return moveState;
    }

    State getBuyToolCardState() {
        return buyToolCardState;
    }

    State getUseToolCardState() {
        return useToolCardState;
    }

    State getEndState() {
        return endState;
    }

    public boolean contains(String username) {
        return (offlinePlayers.contains(username));
    }

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

    public void sendMessage(String name, String message) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(name)) {
                virtualView.displayMessage(message);
            }
        }
    }

    public void sendActiveTableElement(String name) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(name)) {
                virtualView.activeTableElement(game.getActiveTableElement());
            }
        }
    }

    public void sendActiveTableElement(String name, String element) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(name)) {
                virtualView.activeTableElement(element);
            }
        }
    }

    @Override
    public synchronized void update(String message) {
        ArrayList<String> commands;
        commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
        if (isGameEnded){
            handleEndGameEvent(commands);
        } else {
            handleEvent(commands);
        }
    }

    @Override
    public synchronized void update(Observable o) {
        //ArrayList<String> commands;
        //commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
        //handleEvent(commands);
    }

    private synchronized void handleEndGameEvent(ArrayList<String> commands) {
        if (commands.size() == 2) {
            String username = commands.remove(0);
            VirtualView playerView = null;
            if (players.contains(username)) {
                for(VirtualView view : views){
                    if(view.getUsername().equals(username)){
                        playerView = view;
                    }
                }
                if (commands.get(0).equals("logout")) {
                    ClientDatabase.getPlayerDatabase().disconnect(username);
                    handleOfflinePlayer(username);
                } else if (commands.get(0).equals("play")) {
                    Lobby lobby = Lobby.getLobby();
                    playerView.deleteObservers();
                    playerView.addObserver(lobby);
                    lobby.addPlayer(username, ClientDatabase.getPlayerDatabase().getPlayerLastTime(username));
                    ClientDatabase.getPlayerDatabase().getPlayerData(username).setPhase(Phase.LOBBY);
                    players.remove(username);
                    views.remove(playerView);
                }
            }
        }
    }

    private synchronized void handleEvent(ArrayList<String> commands) {
        if (commands.size() > 1) {
            String username = commands.remove(0);
            if (players.contains(username)) {
                if (!offlinePlayers.contains(username) && commands.get(0).equals("exit") && commands.size() == 1) {
                    state.exitGame(username);
                } else if (offlinePlayers.contains(username) && commands.get(0).equals("join") && commands.size() == 1) {
                    state.joinGame(username);
                } else if (!offlinePlayers.contains(username) && commands.get(0).equals("logout") && commands.size() == 1) {
                    ClientDatabase.getPlayerDatabase().disconnect(username);
                } else if (!offlinePlayers.contains(username)) {
                    state.handleEvent(username, commands);
                }
            }
        }
    }

    ArrayList<String> getOfflinePlayers() {
        return offlinePlayers;
    }

    void deleteObserver(String username) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(username)) {
                game.deleteObserver(virtualView);
                return;
            }
        }
    }

    void addObserver(String username) {
        for (VirtualView virtualView : views) {
            if (virtualView.getUsername().equals(username)) {
                game.addObserver(virtualView);
                return;
            }
        }
    }

    void skipTurn() {
        if (!game.isGameEnded())
            game.skipTurn();
        checkGameState();
    }

    void itsYourTurn() {
        sendMessage(game.getCurrentPlayer(), ITS_YOUR_TURN);
    }

    synchronized void checkGameState() {
        String player = game.getCurrentPlayer();
        if (game.isTurnEnded()) {
            if (game.isRoundEnded() && game.isGameEnded()) {
                game.countScores();
                sendMessage(game.getWinner(), YOU_WON);
                state = endState;
                timer.cancel();
                endGame();
                return;
            }
            if (!offlinePlayers.contains(game.getCurrentPlayer())) {
                if(player != null) sendActiveTableElement(player, INACTIVE_TABLE);
                setTimerSkipTurn();
            }
        }

        if (!game.isGameEnded() && offlinePlayers.contains(game.getCurrentPlayer())) {
            skipTurn();
        } else if (offlinePlayers.size() == players.size() - 1) {
            if (!game.isGameEnded()) {
                String lastPlayer = game.getCurrentPlayer();
                game.endGame();
                sendMessage(lastPlayer, YOU_WON);
                endGame();
            }
            state = endState;
            timer.cancel();
        } else {
            state = chooseActionState;
            sendActiveTableElement(game.getCurrentPlayer(), CHOOSE_ACTION);
            itsYourTurn();
        }
    }

    void setTimerSkipTurn() {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = chooseActionState;
                offlinePlayers.add(game.getCurrentPlayer());
                //sendActiveTableElement(game.getCurrentPlayer(), CHOOSE_ACTION);
                sendMessage(game.getCurrentPlayer(), YOU_LEFT_THE_GAME);
                sendActiveTableElement(game.getCurrentPlayer(), JOIN_ACTION);
                skipTurn();
            }
        }, turnTimerSeconds * 1000);
    }

    private void endGame() {
        handleOfflinePlayers();
        isGameEnded = true;
        handleOnlinePlayers();
    }

    private void handleOnlinePlayers() {
        for (String player : players) {
            if (!offlinePlayers.contains(player)) {
                sendMessage(player, "Choose [play] to play again, [logout] to go back to login");
            }
        }
    }

    private void handleOfflinePlayers() {
        Lobby.getLobby().removeController(this);
        for (String offlinePlayer : offlinePlayers) {
            handleOfflinePlayer(offlinePlayer);
        }
    }

    private void handleOfflinePlayer(String username){
        VirtualViewsDataBase virtualViewsDataBase = VirtualViewsDataBase.getVirtualViewsDataBase();
        ClientData client = ClientDatabase.getPlayerDatabase().findPlayer(username);
        if (!client.isConnected()) {
            virtualViewsDataBase.getVirtualView(username).deleteObservers();
            virtualViewsDataBase.removeVirtualView(username);
            client.setPhase(Phase.LOGIN);
        }
    }

    Game getGame() {
        return game;
    }
}
