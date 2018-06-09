package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.view.View;

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
        while(on){
            String tempMessage = connection.getMessage();
            if(tempMessage.equals("ping")){
                connection.sendMessage("pong");
            } else if(lock){
                setMessage(tempMessage);
            } else {
                if(readable())
                    handleCommands(new ArrayList<>(Arrays.asList(getMessage().split("\\s*/\\s*"))));
                handleCommands(new ArrayList<>(Arrays.asList(tempMessage.split("\\s*/\\s*"))));
            }
        }
    }

    private void handleCommands(ArrayList<String> commands) {
        if (commands.remove(0).equals("game")) {
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
