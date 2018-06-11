package it.polimi.ingsw.controller;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.ParserManager;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.VirtualView;
import java.util.*;

public class Controller implements Observer {


    public static final int TIMER_SECONDS = 20*60;
    public static final String INVALID_FORMAT = "Command of invalid format.";
    public static final String WAIT_YOUR_TURN = "Wait your turn.";
    public static final String CHOOSE_TOOL_CARD = "Choose the tool card to use (0-1-2).";
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
    private ArrayList<String> names;
    private ArrayList<String> offlinePlayers;
    private Game game;

    public Controller(int matchID, ArrayList<VirtualView> views) {
        offlinePlayers = new ArrayList<>();
        ParserManager pm = ParserManager.getParserManager();
        names = new ArrayList<>();
        for (VirtualView view: views) {
            names.add(view.getUsername());
        }
        game = new Game(matchID, names);
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
                view.setPrivateObjectiveCard(privateObjectiveCard);
                view.displayMessage("Scegli tra una delle seguenti PatternCard: (0-1-2-3)");
                for (PatternCard patternCard : patterns) {
                    view.displayPatternCard(patternCard);
                }
            } catch (NotValidInputException e) {
                System.out.println("Player Inesistente.");
            }
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (String name: names) {
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
            }}, TIMER_SECONDS*1000);
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
            if (names.contains(username)) {
                if (commands.get(0).equals("quit") && commands.size()==1) {
                    //TODO
                    return;
                } else if (commands.get(0).equals("join") && commands.size()==1) {
                    //TODO
                    return;
                } else {
                    state.handleEvent(username, commands);
                }
            }
        }
    }

    public void skipTurn() {
        game.skipTurn();
        checkGameState();
    }

    protected void itsYourTurn() {
        sendMessage(game.getCurrentPlayer(), "It's your turn! Choose Action: move, toolcard, skip");
    }

    public void checkGameState() {
        if (game.isTurnEnded()) {
            if (game.isRoundEnded()) {
                if (game.isGameEnded()) {
                    game.countScores();
                    state = endState;
                    timer.cancel();
                    return;
                }
            }
        setTimerSkipTurn();
        }
        state = chooseActionState;
        itsYourTurn();
    }

    public void setTimerSkipTurn() {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = chooseActionState;
                skipTurn();
            }
        }, TIMER_SECONDS*1000);
    }

    protected Game getGame() {
        return game;
    }
}
