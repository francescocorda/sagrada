package it.polimi.ingsw.controller;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.ParserManager;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.VirtualView;
import java.util.*;

public class Controller implements Observer {

    private enum STATES {
        START,
        CHOOSE_ACTION,
        MOVE,
        BUY_TOOLCARD,
        USE_TOOLCARD,
        END;
    }

    private static final int TIMER_SECONDS = 60;
    private static final String INVALID_FORMAT = "Command of invalid format.";
    private static final String WAIT_YOUR_TURN = "Wait your turn.";
    private static final String CHOOSE_TOOL_CARD = "Choose the tool card to use (0-1-2)";
    private static final int CHOOSE_ACTION_DIM = 1;

    private ArrayList<VirtualView> views;
    Timer timer;
    ArrayList<String> names;
    private Game game;
    private STATES state;

    public Controller(int matchID, ArrayList<VirtualView> views) {
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
        state = STATES.START;
        timer = new Timer();
        startGame();
    }

    private void startGame() {
        game.drawPublicObjectiveCards();
        game.drawToolCards();
        for (VirtualView view: views) {
            game.drawDices();
            PrivateObjectiveCard privateObjectiveCard = null;
            try {
                privateObjectiveCard = game.assignPrivateObjectiveCard(view.getUsername());
                ArrayList<PatternCard> patterns = game.drawPatternCards();
                view.setPrivateObjectiveCard(privateObjectiveCard);
                view.displayMessage("Scegli tra una delle seguenti PatternCard: (0-1)");
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
                    ArrayList<String> commands = new ArrayList<>();
                    commands.add("0");
                    assignPatternCard(name, commands);
                }
                game.doneAssignPatternCards();
                state = STATES.CHOOSE_ACTION;
                yourTurn();
            }}, TIMER_SECONDS*1000);
    }


    public void sendMessage(String name, String message) {
        for (VirtualView virtualView: views) {
            if (virtualView.getUsername().equals(name)) {
                virtualView.displayMessage(message);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        ArrayList<String> commands =  new ArrayList<>();
        if(o instanceof VirtualView) {
            if(arg instanceof String) {
                String message = (String) arg;
                commands = new ArrayList<>(Arrays.asList(message.split("\\s*,\\s*")));
                eventListener(commands);
            }
        }
    }

    private void eventListener(ArrayList<String> commands) {
        if(commands.size()>1){
            String username = commands.remove(0);
            if (names.contains(username)) {
                if (commands.get(0).equals("quit")) {
                    //TODO
                    return;
                } else if (commands.get(0).equals("join")) {
                    //TODO
                    return;
                } else {
                    switch (state) {
                        case START:
                            assignPatternCard(username, commands);
                            break;
                        case CHOOSE_ACTION:
                            if (game.isCurrentPlayer(username)) {
                                if (commands.size() == CHOOSE_ACTION_DIM) {
                                    switch (commands.remove(0)) {
                                        case "move":
                                            sendMessage(username, "Choose the dice to place in the window: insert draft pool index and window coordinates");
                                            state = STATES.MOVE;
                                            break;
                                        case "skip":
                                            sendMessage(username, "Turn skipped.");
                                            skipTurn();
                                            break;
                                        case "toolcard":
                                            sendMessage(username, CHOOSE_TOOL_CARD);
                                            state = STATES.BUY_TOOLCARD;
                                            break;
                                        default:
                                            yourTurn();
                                            break;
                                    }
                                } else {
                                    sendMessage(username, INVALID_FORMAT);
                                }
                            } else {
                                sendMessage(username, WAIT_YOUR_TURN);
                            }
                            break;
                        case MOVE:
                            if (game.isCurrentPlayer(username)) {
                                if (game.moveAllowed()) {
                                    performMove(username, commands);
                                } else {
                                    state = STATES.CHOOSE_ACTION;
                                    yourTurn();
                                }
                            } else {
                                sendMessage(username, WAIT_YOUR_TURN);
                            }
                            break;
                        case BUY_TOOLCARD:
                            if (game.isCurrentPlayer(username)) {
                                buyToolCard(username, commands);
                            } else {
                                sendMessage(username, WAIT_YOUR_TURN);
                            }
                            break;
                        case USE_TOOLCARD:
                            if (game.isCurrentPlayer(username)) {
                                useToolCard(username, commands);
                                if (!game.isToolCardActive()) {
                                    checkGameState();
                                }
                            }  else {
                                sendMessage(username, WAIT_YOUR_TURN);
                            }
                            break;
                        case END:
                            game.countScores();
                    }
                }
            }
        }
    }

    private void useToolCard(String username, ArrayList<String> commands) {
        if (commands.size() == game.getToolCardCommandsSize() && checkFormat(commands)) {
            game.useToolCard(commands);
        } else {
            sendMessage(username, INVALID_FORMAT);
        }
    }

    private void buyToolCard(String username, ArrayList<String> commands) {
        if(commands.size() == 1 && checkFormat(commands)) {
            int index = Integer.parseInt(commands.remove(0));
            if (game.toolCardUseAllowed(index) && game.buyToolCard(index)) {
                game.useToolCard(new ArrayList<>());
                if (game.isToolCardActive()) {
                    state = STATES.USE_TOOLCARD;
                } else {
                    yourTurn();
                    state = STATES.CHOOSE_ACTION;
                }
            } else {
                state = STATES.CHOOSE_ACTION;
                yourTurn();
            }
        } else {
            sendMessage(username, INVALID_FORMAT);
            sendMessage(username, CHOOSE_TOOL_CARD);
        }
    }

    public void skipTurn() {
        game.skipTurn();
        checkGameState();
    }


    public void performMove(String username, ArrayList<String> commands) {
        if (commands.size()==3) {
            try {
                for (String command : commands) {
                    Integer.parseInt(command);
                }
                if(!game.performMove(commands)){
                    yourTurn();
                }
            } catch (NumberFormatException e) {
                sendMessage(username, INVALID_FORMAT);
                yourTurn();
            }
        } else {
            sendMessage(username, INVALID_FORMAT);
            yourTurn();
        }
        checkGameState();
    }

    private void yourTurn() {
        sendMessage(game.getCurrentPlayer(), "It's your turn! Choose Action: move / toolcard / skip");
    }

    private boolean checkFormat(ArrayList<String> commands) {
        try {
            for (String command : commands) {
                Integer.parseInt(command);
            }
            return true;
        } catch (NumberFormatException e) {
            //
        }
        return false;
    }

    private synchronized void assignPatternCard(String username, ArrayList<String> commands) {

        int indexPattern = Integer.parseInt(commands.remove(0));
        if ((indexPattern == 0 || indexPattern == 1) && commands.isEmpty()) {
            try {
                game.setPatternCard(username, indexPattern);
                sendMessage(username, "Pattern card assigned.");
            } catch (NotValidInputException e) {
                sendMessage(username, INVALID_FORMAT);
            }
        } else {
            sendMessage(username, INVALID_FORMAT);
        }
        if (game.doneAssignPatternCards()) {
            timer.cancel();
            state = STATES.CHOOSE_ACTION;
            yourTurn();
            return;
        }
    }

    public void checkGameState() {
        if (game.isTurnEnded()) {
            if (game.isRoundEnded()) {
                if (game.isGameEnded()) {
                    game.countScores();
                    state = STATES.END;
                } else {
                    yourTurn();
                    state = STATES.CHOOSE_ACTION;
                }
            } else {
                state = STATES.CHOOSE_ACTION;
                yourTurn();
            }
        } else {
            state = STATES.CHOOSE_ACTION;
            yourTurn();
        }
    }
}
