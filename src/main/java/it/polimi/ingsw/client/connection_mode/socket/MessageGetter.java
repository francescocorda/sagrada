package it.polimi.ingsw.client.connection_mode.socket;

import com.google.gson.Gson;
import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageGetter extends Thread {
    private String message;
    private boolean wait;
    private static final Object countLock = new Object();
    private ConnectionSocket connection;
    private boolean on;
    private boolean lock;
    private View view;
    private Gson gson;

    /**
     * creates a new {@link MessageGetter} given a {@link ConnectionSocket} connection and a
     * {@link View} view.
     * it does that by setting {@link #connection} as the given connection,
     * {@link #view} as the given view, as true all boolean parameters and initialising {@link #gson}
     * as a new {@link Gson}
     * @param connection : is the given {@link ConnectionSocket}
     * @param view : is the given {@link View}
     */
    public MessageGetter(ConnectionSocket connection, View view) {
        this.connection = connection;
        this.view = view;
        message = new String();
        wait = true;
        on = true;
        lock = true;
        gson = new Gson();
        start();
    }

    /**
     * handles messages from {@link #connection}.
     * it does that by the use of method {@link ConnectionSocket#getMessage()} while {@link #on} is true,
     * once a message is arrived it check if it is "ping" or not. in the first case it automatically responses
     * by sending a "pong" message through {@link ConnectionSocket#sendMessage(String)}; otherwise if
     * {@link #lock} param is true checks if the gotten message does not contains "welcome" or "back_to_game" it sets
     * that message in a Buffer through {@link #setMessage(String)}; otherwise it shows a Welcome message and set
     * {@link #lock} to false; if {@link #lock} is false it extracts all commands from the gotten message and
     * gives the resulting {@link ArrayList<String>} to method {@link #handleCommands(ArrayList)}
     */
    @Override
    public void run() {
        super.run();
        ArrayList<String> check;
        try {
            while (on) {
                String tempMessage = connection.getMessage();
                if (tempMessage.equals("ping")) {
                    connection.sendMessage("pong");
                } else if (lock) {
                    check = new ArrayList<>(Arrays.asList(tempMessage.split("\\s*/\\s*")));
                    if ((check.size() > 1 && check.get(1).equals("welcome")) || (check.size() == 1 && check.get(0).equals("back_to_game"))) {
                        lock = false;
                        if (check.size()>1) {
                            view.displayMessage("Welcome!");
                        } else {
                            view.displayMessage("Welcome Back!");
                        }
                    } else
                        setMessage(tempMessage);
                } else {
                    handleCommands(new ArrayList<>(Arrays.asList(tempMessage.split("\\s*/\\s*"))));
                }
            }
        } catch (NullPointerException e) {
            System.out.println("server Offline");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * handles given {@link ArrayList<String>} commands.
     * @param commands : the given {@link ArrayList<String>}
     */
    private synchronized void handleCommands(ArrayList<String> commands) {
        String phase = commands.get(0);
        if (phase.equals("lobby")) {
            commands.remove(0);
            lobby(commands);
        } else {
            game(commands);
        }
    }

    /**
     * handles given {@link ArrayList<String>} commands that are game related.
     * @param commands : the given {@link ArrayList<String>}
     */
    private void game(ArrayList<String> commands) {
        Table table;
        String command = commands.remove(0);
        if(command.equals("game")){
            switch (commands.remove(0)) {
                case "displayGame":
                    table = gson.fromJson(commands.remove(0), Table.class);
                    view.displayGame(table);
                    break;
                case "pattern_card":
                    PatternCard patternCard = gson.fromJson(commands.get(0), PatternCard.class);
                    view.displayPatternCard(patternCard);
                    break;
                case "private_objective_card":
                    PrivateObjectiveCard pOCard = gson.fromJson(commands.get(0), PrivateObjectiveCard.class);
                    view.displayPrivateObjectiveCard(pOCard);
                    break;
                case "active_table_element":
                    if(!commands.isEmpty() && commands.get(0).equals("JOIN"))
                        lock = true;
                    view.activeTableElement(commands.remove(0));
                    break;
                case "update":
                    String observable = commands.remove(0);
                    table = gson.fromJson(observable, Table.class);
                    view.update(table);
                    break;
                default:
                    break;
            }
        } else {
            view.displayMessage(command);
        }
    }

    /**
     * handles given {@link ArrayList<String>} commands that are lobby related.
     * @param commands : the given {@link ArrayList<String>}
     */
    private void lobby(ArrayList<String> commands) {
        String command = commands.remove(0);
        switch (command) {
            case "player_joined":
                view.displayMessage("Player joined: " + commands.remove(0));
                break;
            case "list_of_players":
                view.displayMessage("LIST OF PLAYERS:\n" + getListOfPlayers(commands));
                break;
            case "player_left":
                view.displayMessage("Player left: " + commands.remove(0));
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
     * @param players : the given {@link ArrayList<String>} of players's username
     * @return a well formatted {@link String} of the given {@link ArrayList<String>} players
     */
    private String getListOfPlayers(ArrayList<String> players) {
        String enclosureSymbol = "-";
        String separatorSymbol = "| ";
        String command = separatorSymbol;
        for (String player : players) {
            command = command.concat(player + " " + separatorSymbol);
        }
        String enclosure = new String();
        for (int i = 1; i < command.length(); i++)
            enclosure = enclosure.concat(enclosureSymbol);
        command = enclosure + "\n" + command + "\n" + enclosure;
        return command;
    }

    /**
     * set the given {@link String} as value of {@link #message}.
     * @param message : the given message
     */
    private void setMessage(String message) {
        while (!wait) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
        synchronized (countLock) {
            this.message = message;
            wait = false;
        }
    }

    /**
     * @return the value of {@link #message}
     */
    public String getMessage() {
        synchronized (countLock) {
            String temp = message;
            message = null;
            wait = true;
            return temp;
        }
    }

    /**
     * @return if {@link #message} can be red
     */
    public boolean readable() {
        synchronized (countLock) {
            return !wait;
        }
    }

    /**
     * closes this {@link MessageGetter}
     */
    public void kill() {
        on = false;
        Thread.currentThread().interrupt();
    }
}
