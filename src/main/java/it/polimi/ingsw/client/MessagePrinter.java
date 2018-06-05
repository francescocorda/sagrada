package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.view.CLIView;
import it.polimi.ingsw.view.View;
import java.util.ArrayList;
import java.util.Arrays;

public class MessagePrinter extends Thread {
    private ConnectionSocket connection;
    private MessageDealer md;
    private View view;
    private Gson gson;
    private String username;

    public MessagePrinter(ConnectionSocket connection, MessageDealer md, View view) {
        this.connection = connection;
        this.md = md;
        this.view = view;
        gson = new Gson();
        start();
    }

    @Override
    public void run() {
        boolean loop = true;
        boolean inGame = false;
        while (loop) {

            String message = connection.getMessage();
            if (message == null) {
                loop = false;
                toScreen("Server closed.");
                connection.close();
            } else {
                switch (message) {
                    case "ping":
                        connection.sendMessage("pong");
                        break;
                    case "login<insert_credentials>":
                        login();
                        break;
                    case "lobby<last_access><insert_last_access>":
                        ((CLIView) view).setUsername(username);
                        lobby();
                        break;
                    case "lobby<welcome>":
                        inGame = true;
                        break;
                    default:
                        if(!inGame)
                            toScreen(message);
                        handleCommands(new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*"))));
                }
            }
        }
    }

    private void handleCommands(ArrayList<String> commands) {
        if (commands.remove(0).equals("game")) {
            switch (commands.remove(0)) {
                case "message":
                    toScreen(commands.remove(0));
                    break;
                case "displayGame":
                    view.displayGame();
                    break;
                case "pattern_card":
                    PatternCard patternCard = gson.fromJson(commands.get(0), PatternCard.class);
                    view.displayPatternCard(patternCard);
                    break;
                case "update":
                    String observer = commands.remove(0);
                    String object = commands.remove(0);
                    Table table = gson.fromJson(observer, Table.class);
                    String message = gson.fromJson(object, String.class);
                    view.update(table, message);
                    break;
                default:
                    for (String command : commands) {
                        toScreen(command);
                    }
            }
        }
    }

    private void lobby() {
        long time = 0;
        boolean isValid = true;
        toScreen("Insert last time you visited a cathedral: ");
        try {
            time = Long.parseLong(readFromInputStream());
        } catch (NumberFormatException e) {
            isValid = false;
        }
        if (isValid) {
            connection.sendMessage("lobby<last_access><" + time + ">");
        }
    }

    private void login() {
        String password;
        toScreen("Insert username: ");
        username = readFromInputStream();
        toScreen("Insert password: ");
        password = readFromInputStream();
        connection.sendMessage("login<" + username + "><" + password + ">");
    }

    private void game() {
        //GAME
    }

    private void toScreen(String message) {
        System.out.println(message);
    }

    private String readFromInputStream() {
        while (md.checkWait()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                toScreen("Error: " + e);
            }
        }
        return md.getMessage();
    }
}
