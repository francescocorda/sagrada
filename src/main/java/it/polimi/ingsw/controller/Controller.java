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
        TOOLCARD,
        PLAY,
        END
    }

    private static final int TIMER_SECONDS = 60;

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
                } else if(commands.get(0).equals("join")) {
                    //TODO
                    return;
                }
                switch (state) {
                    case START:
                        assignPatternCard(username, commands);
                        //if (commands.get(0).equals("PATTERN")) {
                        //  commands.remove(0);
                        //assignPatternCard(username, commands);
                        //}
                        break;
                    case CHOOSE_ACTION:
                        if(game.isCurrentPlayer(username)) {
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
                                    useToolCard();
                                    break;
                                default:
                                    yourTurn();
                            }
                        }
                        break;
                    case MOVE:
                        performMove(commands);
                        state = STATES.CHOOSE_ACTION;
                        break;
                    case END:
                        game.countScores();
                }
            }
        }
    }

    public void skipTurn() {
        game.skipTurn();
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
    }


    public void performMove(ArrayList<String> commands) {
        if (commands.size()==3) {
            try {
                Integer.parseInt(commands.get(0));
                Integer.parseInt(commands.get(1));
                Integer.parseInt(commands.get(2));
                if(!game.performMove(commands)){
                    yourTurn();
                }
            } catch (NumberFormatException e) {
                sendMessage(game.getCurrentPlayer(), "Command of invalid format.");
            }
        } else {
            sendMessage(game.getCurrentPlayer(), "Command of invalid format.");
        }
        checkGameState();
    }

    private void broadcast(String message) {
        for (VirtualView virtualView: views) {
            virtualView.displayMessage(message);
        }
    }
    private void yourTurn() {
        sendMessage(game.getCurrentPlayer(), "It's your turn! Choose Action: move / toolcard / skip");
    }

    private void useToolCard() {
        //TODO
    }

    private void assignPatternCard(String username, ArrayList<String> commands) {

        int indexPattern = Integer.parseInt(commands.remove(0));
        if ((indexPattern == 0 || indexPattern == 1) && commands.isEmpty()) {
            try {
                game.setPatternCard(username, indexPattern);
                sendMessage(username, "Pattern card assigned.");
            } catch (NotValidInputException e) {
                System.out.println("Player " + username + ": Invalid Command.");
                broadcast("Invalid Command.");
            }
        } else {
            broadcast("Invalid Command.");
        }
        if (game.doneAssignPatternCards()) {
            timer.cancel();
            state = STATES.CHOOSE_ACTION;
            yourTurn();
            return;
        }
    }

    public void checkGameState() {
        if (game.isTurnEnded()){
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
        }
    }
}
