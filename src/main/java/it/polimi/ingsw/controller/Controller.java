package it.polimi.ingsw.controller;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.ParserManager;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.VirtualView;
import java.util.*;

public class Controller implements Observer {


    public static final int TIMER_SECONDS = 120;
    public static final String INVALID_FORMAT = "Command of invalid format.";
    public static final String WAIT_YOUR_TURN = "Wait your turn.";
    public static final String CHOOSE_TOOL_CARD = "Choose the tool card to use (0-1-2).";
    public static final String CHOOSE_PATTERN_CARD = "Choose the pattern card to use (0-1-2-3).";
    public static final String PLAYER_NOT_FOUND = "Player not found.";
    public static final String ITS_YOUR_TURN = "It's your turn! Choose Action: move, toolcard, skip.";
    public static final String ACTION_CANCELED = "Action canceled.";
    public static final String LEFT_THE_GAME = " left the game.";
    public static final String YOU_LEFT_THE_GAME = "You left the game. Choose join to get back.";
    public static final String JOINED_THE_GAME = " joined the game.";
    public static final String GAME_JOINED = "Game joined.";
    public static final String BACK_TO_GAME = "back_to_game";
    public static final String YOU_WON = "You Won!";
    public static final int CHOOSE_ACTION_DIM = 1;

    private State startState;
    private State chooseActionState;
    private State moveState;
    private State buyToolCardState;
    private State useToolCardState;
    private State endState;

    private State state;

    private ArrayList<VirtualView> views;
    private Timer timer;
    private ArrayList<String> players;
    private ArrayList<String> offlinePlayers;
    private Game game;

    public Controller(int matchID, ArrayList<VirtualView> views) {
        offlinePlayers = new ArrayList<>();
        ParserManager pm = ParserManager.getParserManager();
        players = new ArrayList<>();
        for (VirtualView view: views) {
            players.add(view.getUsername());
        }
        game = new Game(matchID, players);
        game.setPatternDeck(pm.getPatternDeck());
        game.setPublicObjectiveDeck(pm.getPublicObjectiveDeck());
        game.setToolCards(pm.getToolCards());
        this.views = views;
        for (VirtualView virtualView: views) {
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getStartState() {
        return startState;
    }

    public State getChooseActionState() {
        return chooseActionState;
    }

    public State getMoveState() {
        return moveState;
    }

    public State getBuyToolCardState() {
        return buyToolCardState;
    }

    public State getUseToolCardState() {
        return useToolCardState;
    }

    public State getEndState() {
        return endState;
    }

    public boolean contains(String username){
        return (offlinePlayers.contains(username));
    }

    private void startGame() {
        game.drawDices();
        game.drawPublicObjectiveCards();
        game.drawToolCards();
        for (VirtualView view: views) {
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
                System.out.println(PLAYER_NOT_FOUND);
            }
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (String name: players) {
                    try {
                        game.setPatternCard(name, 0);
                    } catch (NotValidInputException e) {
                        //exception already handled
                    }
                }
                game.doneAssignPatternCards();
                state = chooseActionState;
                itsYourTurn();
                setTimerSkipTurn();
            }
            }, TIMER_SECONDS*1000);
    }

    public void sendMessage(String name, String message) {
        for (VirtualView virtualView: views) {
            if (virtualView.getUsername().equals(name)) {
                virtualView.displayGameMessage(message);
            }
        }
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        ArrayList<String> commands;
        if(o instanceof VirtualView) {
            if(arg instanceof String) {
                String message = (String) arg;
                commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
                handleEvent(commands);
            }
        }
    }

    private synchronized void handleEvent(ArrayList<String> commands) {
        if(commands.size()>1){
            String username = commands.remove(0);
            if (players.contains(username)) {
                if (commands.get(0).equals("exit") && commands.size()==1) {
                    if (!offlinePlayers.contains(username)) {
                        offlinePlayers.add(username);
                    }
                    deleteObserver(username);
                    game.notifyObservers(username + LEFT_THE_GAME);
                    sendMessage(username, YOU_LEFT_THE_GAME);
                    if (game.getCurrentPlayer().equals(username)) {
                        skipTurn();
                    }
                    return;
                } else if (offlinePlayers.contains(username) && commands.get(0).equals("join") && commands.size()==1) {
                    offlinePlayers.remove(username);
                    sendMessage(username, BACK_TO_GAME);
                    sendMessage(username, GAME_JOINED);
                    game.notifyObservers(username + JOINED_THE_GAME);
                    addObserver(username);
                    return;
                } else if(!offlinePlayers.contains(username)){
                    state.handleEvent(username, commands);
                }
            }
        }
    }

    public void deleteObserver(String username) {
        for (VirtualView virtualView: views) {
            if (virtualView.getUsername().equals(username)) {
                game.deleteObserver(virtualView);
                return;
            }
        }
    }

    public void addObserver(String username) {
        for (VirtualView virtualView: views) {
            if (virtualView.getUsername().equals(username)) {
                game.addObserver(virtualView);
                return;
            }
        }
    }

    public void skipTurn() {
        game.skipTurn();
        checkGameState();
    }

    protected void itsYourTurn() {
        sendMessage(game.getCurrentPlayer(), ITS_YOUR_TURN);
    }

    public void checkGameState() {
        if (game.isTurnEnded()) {
            if (game.isRoundEnded()) {
                if (game.isGameEnded()) {
                    game.countScores();
                    sendMessage(game.getWinner(), YOU_WON);
                    state = endState;
                    timer.cancel();
                    return;
                }
            }
            if(!offlinePlayers.contains(game.getCurrentPlayer())) {
                setTimerSkipTurn();
            }
        }

        if(offlinePlayers.contains(game.getCurrentPlayer())) {
            skipTurn();
        } else if (offlinePlayers.size() == players.size()-1) {
            sendMessage(game.getCurrentPlayer(), YOU_WON);
            game.endGame();
            state = endState;
            timer.cancel();
        } else {
            state = chooseActionState;
            itsYourTurn();
        }
    }

    public void setTimerSkipTurn() {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = chooseActionState;
                offlinePlayers.add(game.getCurrentPlayer());
                sendMessage(game.getCurrentPlayer(), YOU_LEFT_THE_GAME);
                skipTurn();
            }
        }, TIMER_SECONDS*1000);
    }

    protected Game getGame() {
        return game;
    }
}
