package it.polimi.ingsw;

import com.sun.media.jfxmedia.logging.Logger;
import it.polimi.ingsw.Server.Connection;
import it.polimi.ingsw.Server.ConnectionMode;

import java.net.SocketException;
import java.util.Timer;

public class ClientController implements Runnable {

    private enum Phase {
        LOGIN,
        LOBBY,
        GAME,
        END_GAME
    }

    private enum Status {
        ONLINE,
        OFFLINE
    }

    private PlayerData players;
    private Connection connection;
    private ConnectionMode connectionMode;
    private String username;
    private Status status;
    private Phase phase;

    public ClientController(Connection connection, ConnectionMode connectionMode, PlayerData players) {
        this.connectionMode = connectionMode;
        this.connection = connection;
        this.players = players;
        this.status = Status.ONLINE;
        this.phase = Phase.LOGIN;
        this.username = "";
    }

    @Override
    public void run() {
        login();
        if (status == Status.ONLINE) {
            System.out.println("User: " + username + " has signed in");
            System.out.println("Connected players: " + players.onlinePlayersNumber());
        }
        nextPhase();
        if (status == Status.ONLINE)
            Lobby.getLobby().joinLobby(this);
        if(phase != Phase.GAME && status == Status.OFFLINE)
            close();
    }

    private void login() {
        MessageReader messageReader;
        sendImportantMessage("login<insert_credentials>");
        while (status == Status.ONLINE) {
            messageReader = getMessage();
            if (messageReader.hasNext()) {
                String tempUsername = messageReader.getNext();
                if (messageReader.hasNext()) {
                    String password = messageReader.getNext();
                    if (!messageReader.hasNext() && players.check(tempUsername, password)) {
                        sendMessage("login<success>");
                        this.username = tempUsername;
                        return;
                    } else {
                        sendMessage("login<failed>");
                    }
                } else {
                    sendMessage("login<invalid_command>");
                }
            } else
                sendMessage("login<invalid_command>");
            sendImportantMessage("login<insert_credentials>");
        }
    }

    private void changeConnectionMode() {
        if (connectionMode == ConnectionMode.SOCKET) {
            connectionMode = ConnectionMode.RMI;
            //connection = new ConnectionRMI();
        } else {
            connectionMode = ConnectionMode.SOCKET;
            //connection = new ConnectionSocket();
        }
        System.out.println(username + " has changed connection to: " + connectionMode);
    }

    public void sendImportantMessage(String message) {
        if (isOnline())
            connection.sendMessage(message);
    }

    public void sendMessage(String message){
        connection.sendMessage(message);
    }

    public MessageReader getMessage() {
        MessageReader messageReader;
        while (status == Status.ONLINE) {
            try {
                messageReader = new MessageReader(connection.getMessage());
            } catch (NullPointerException e) {
                messageReader = new MessageReader("quit");
            }
            if (messageReader.hasNext()) {
                String declaredTAG = messageReader.getNext();
                switch (declaredTAG) {
                    case "change_communication_mode":
                        changeConnectionMode();
                        break;
                    case "quit":
                        connection.close();
                        status = Status.OFFLINE;
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

    private MessageReader playerOffline(){
        if (username.equals("")) {
            System.out.println("Client closed connection");
        } else {
            players.disconnect(username);
            System.out.println("User: " + username + " logged out");
            if (phase == Phase.GAME) {
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
        Thread.currentThread().isInterrupted();
    }

    public boolean isOnline() {
        checkConnection();
        return (status == Status.ONLINE);
    }

    public void nextPhase() {
        switch (phase) {
            case GAME:
                phase = Phase.END_GAME;
                break;
            case LOBBY:
                phase = Phase.GAME;
                break;
            case LOGIN:
                phase = Phase.LOBBY;
                break;
            case END_GAME:
                phase = Phase.LOGIN;
                break;
        }
    }

    private void checkConnection() {
        sendMessage("ping");
        MessageReader messageReader = getMessage();
        while (!messageReader.getNext().equals("pong") && status == Status.ONLINE) {
            sendMessage("ping");
            messageReader = getMessage();
        }
    }
}