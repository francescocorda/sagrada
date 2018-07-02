package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Color;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.view.View;
import org.apache.commons.lang.ObjectUtils;

import java.io.IOException;
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

    public MessageGetter(ConnectionSocket connection, View view) {
        this.connection = connection;
        this.view = view;
        this.message = new String();
        this.wait = true;
        this.on = true;
        this.lock = true;
        this.gson = new Gson();
        start();
    }

    @Override
    public void run() {
        super.run();
        ArrayList<String> check;
        boolean unlock = false;
        try {
            while (on) {
                String tempMessage = connection.getMessage();
                if (tempMessage.equals("ping")) {
                    connection.sendMessage("pong");
                } else if (lock) {
                    check = new ArrayList<>(Arrays.asList(tempMessage.split("\\s*/\\s*")));
                    if (check.size() > 1 && (check.get(1).equals("welcome") || check.get(1).equals("back_to_game"))) {
                        unlock = true;
                        if (check.get(1).equals("welcome"))
                            view.displayMessage("Welcome!");
                        else
                            view.displayMessage("Welcome Back!");
                    } else
                        setMessage(tempMessage);
                } else {
                    handleCommands(new ArrayList<>(Arrays.asList(tempMessage.split("\\s*/\\s*"))));
                }
                if (unlock)
                    unlock();
            }
        } catch (NullPointerException e) {
            System.out.println("Server Offline");
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void handleCommands(ArrayList<String> commands) {
        System.out.println(commands.size());
        String phase = commands.remove(0);
        if (phase.equals("game")) {
            game(commands);
        } else if (phase.equals("lobby")) {
            lobby(commands);
        }
    }

    private void game(ArrayList<String> commands) {
        Table table;
        switch (commands.remove(0)) {
            case "message":
                view.displayGameMessage(commands.remove(0));
                break;
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
                view.activeTableElement(commands.remove(0));
                break;
            case "update":
                String observable = commands.remove(0);
                String message = null;
                if (!commands.isEmpty())
                    message = commands.remove(0);
                table = gson.fromJson(observable, Table.class);
                view.update(table, message);
                break;
            default:
                String recomposedMessage = String.join("/", commands);
                System.out.println("ERROR: arrived message: " + recomposedMessage);
        }
    }

    private void lobby(ArrayList<String> commands) {
        String message = commands.remove(0);
        switch (message) {
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

    private String getListOfPlayers(ArrayList<String> players) {
        String enclosureSymbol = "-";
        String separatorSymbol = "| ";
        String message = separatorSymbol;
        for (String player : players) {
            message = message.concat(player + " " + separatorSymbol);
        }
        String enclosure = new String();
        for (int i = 1; i < message.length(); i++)
            enclosure = enclosure.concat(enclosureSymbol);
        message = enclosure + "\n" + message + "\n" + enclosure;
        return message;
    }

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

    public String getMessage() {
        synchronized (countLock) {
            String temp = message;
            message = null;
            wait = true;
            return temp;
        }
    }

    public boolean readable() {
        synchronized (countLock) {
            return !wait;
        }
    }

    public void kill() {
        this.on = false;
        Thread.currentThread().interrupt();
    }

    public void unlock() {
        this.lock = false;
    }
}
