package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.view.View;
import org.apache.commons.lang.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MessageGetter extends Thread{
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
        try {
            while (on) {
                String tempMessage = connection.getMessage();
                if (tempMessage.equals("ping")) {
                    connection.sendMessage("pong");
                } else if (lock) {
                    setMessage(tempMessage);
                } else {
                    if (readable())
                        handleCommands(new ArrayList<>(Arrays.asList(getMessage().split("\\s*/\\s*"))));
                    handleCommands(new ArrayList<>(Arrays.asList(tempMessage.split("\\s*/\\s*"))));
                }
            }
        } catch (NullPointerException e){
            System.out.println("Server Offline");
            Thread.currentThread().interrupt();
        }
    }

    private void handleCommands(ArrayList<String> commands) {
        String phase = commands.remove(0);
        if (phase.equals("game")) {
            game(commands);
        } else if (phase.equals("lobby")){
            lobby(commands);
        }
    }

    private void game(ArrayList<String> commands){
        switch (commands.remove(0)) {
            case "message":
                view.displayGameMessage(commands.remove(0));
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
                String recomposedMessage = String.join("/", commands);
                System.out.println("ERROR: arrived message: "+recomposedMessage);
        }
    }

    private void lobby(ArrayList<String> commands){
        switch (commands.remove(0)){
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
            case "timer_start":
                view.displayMessage("TIMER START!");
                break;
            case "timer_restarted":
                view.displayMessage("TIMER RESTART!");
                break;
            default:
                break;
        }
    }

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

    private void setMessage(String message) {
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

    public void kill(){
        this.on = false;
        Thread.currentThread().interrupt();
    }

    public void unlock(){
        this.lock=false;
    }
}
