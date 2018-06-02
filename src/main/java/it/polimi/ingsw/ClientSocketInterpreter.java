package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.view.VirtualView;

import static it.polimi.ingsw.Status.*;
import static it.polimi.ingsw.Phase.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;
import java.net.Socket;
import java.util.Timer;

public class ClientSocketInterpreter implements Runnable {

    private ConnectionSocket connection;
    private ClientHandlerSocket handler;
    private PlayerDatabase players;
    private Status status;
    private String username;
    private Phase phase;


    public ClientSocketInterpreter(Socket socket, ClientHandlerSocket handler) {
        this.connection = new ConnectionSocket(socket);
        this.handler = handler;
        players = PlayerDatabase.getPlayerDatabase();
        status = ONLINE;
        phase = LOGIN;
    }


    @Override
    public void run() {
        login();
        nextPhase();
        if (status == Status.ONLINE)
            joinLobby();
        game();
        if (players.getPhase(username) != Phase.GAME && status == Status.OFFLINE)
            close();
    }

    private void login() {
        MessageReader messageReader;
        sendMessage("login<insert_credentials>");
        while (status == ONLINE) {
            messageReader = getMessage();
            if (messageReader.hasNext()) {
                String tempUsername = messageReader.getNext();
                if (!tempUsername.equals("") && messageReader.hasNext()) {
                    String password = messageReader.getNext();
                    if (!password.equals("") && !messageReader.hasNext()) {
                        try {
                            handler.login(tempUsername, password, this);
                            sendMessage("login<success>");
                            this.username = tempUsername;
                            return;
                        } catch (NotValidInputException e) {
                            sendMessage("login<failed>");
                        }
                    } else {
                        sendMessage("login<failed>");
                    }
                } else {
                    sendMessage("login<invalid_command>");
                }
            } else
                sendMessage("login<invalid_command>");
            sendMessage("login<insert_credentials>");
        }
    }

    public void joinLobby() {
        final String invalidCommand = "lobby<invalid_command>";
        long systemTime = System.currentTimeMillis() / 1000; //current unix time in seconds
        String tempMessage;
        while (true) {
            sendMessage("lobby<last_access><insert_last_access>");
            MessageReader messageReader = getMessage();
            if (messageReader.hasNext()) {
                tempMessage = messageReader.getNext();
                if (tempMessage.equals("last_access")) {
                    if (messageReader.hasNext()) {
                        String timeString = messageReader.getNext();
                        boolean isValid = true;
                        long time = 0;
                        try {
                            time = Long.parseLong(timeString);
                        } catch (NumberFormatException e) {
                            isValid = false;
                        }
                        if (isValid && systemTime > time) {
                            try {
                                handler.joinLobby(username, time);
                                break;
                            } catch (NotValidInputException e) {
                                sendMessage(invalidCommand);
                            }
                        } else {
                            sendMessage("lobby<last_access><invalid_time>");
                        }
                    } else {
                        sendMessage(invalidCommand);
                    }
                } else {
                    if (tempMessage.equals("quit"))
                        break;
                    else
                        sendMessage(invalidCommand);
                }
            } else {
                sendMessage(invalidCommand);
            }
        }
    }

    public void sendMessage(String message) {
        if (status == ONLINE && isOnline())
            connection.sendMessage(message);
    }

    public MessageReader getMessage() {
        MessageReader messageReader;
        while (status == ONLINE) {
            try {
                messageReader = new MessageReader(connection.getMessage());
            } catch (NullPointerException e) {
                messageReader = new MessageReader("quit");
            }
            if (messageReader.hasNext()) {
                String declaredTAG = messageReader.getNext();
                switch (declaredTAG) {
                    case "quit":
                        connection.close();
                        status = Status.OFFLINE;
                        players.disconnect(username);
                        break;
                    case "pong":
                        if (!messageReader.hasNext())
                            return new MessageReader("pong");
                        else
                            return messageReader;
                    default:
                        if (declaredTAG.equals(getTag()))
                            return messageReader;
                        else
                            return new MessageReader(declaredTAG);
                }
            }
        }
        return playerOffline();
    }

    private MessageReader playerOffline() {
        if (username == null || username.equals("")) {
            System.out.println("client closed connection");
        } else {
            players.disconnect(username);
            System.out.println("User: " + username + " logged out");
            if (players.getPhase(username) == GAME) {
                return new MessageReader("<end_turn>");
            }
        }
        return new MessageReader("quit");
    }

    public String getUsername() {
        return username;
    }

    private String getTag() {
        switch (phase) {
            case GAME:
                return "game";
            case LOBBY:
                return "lobby";
            case LOGIN:
                return "login";
            default:
                return "login";
        }
    }

    public void close() {
        this.connection.close();
        Thread.currentThread().interrupt();
    }

    public boolean isOnline() {
        checkConnection();
        if (status == OFFLINE && players.contain(username))
            players.removeSocketClient(username);
        return (status == ONLINE);
    }

    private void checkConnection() {
        connection.sendMessage("ping");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                status = Status.OFFLINE;
            }
        }, (long) 30 * 1000);
        MessageReader messageReader = getMessage();
        while (!messageReader.getNext().equals("pong") && status == ONLINE) {
            connection.sendMessage("ping");
            messageReader = getMessage();
        }
        timer.cancel();
    }

    public void nextPhase() {
        switch (phase) {
            case GAME:
                phase = END_GAME;
                break;
            case LOBBY:
                phase = GAME;
                break;
            case LOGIN:
                phase = LOBBY;
                break;
            case END_GAME:
                phase = LOGIN;
                break;
            default:
                phase = LOGIN;
                break;
        }
    }

    private void game() {
        MessageReader reader;
        String message;
        while (status == ONLINE) {
            if (phase == GAME) {
                message = "" + username + "/";
                reader = getMessage();
                if (reader.hasNext()){
                    message = message + reader.getNext();
                    ArrayList<String> commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
                    VirtualView virtualView = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(commands.get(0));
                    virtualView.notifyObservers(message);
                }
            }
        }
    }
}
