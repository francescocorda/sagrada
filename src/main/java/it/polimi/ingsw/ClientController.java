package it.polimi.ingsw;

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

    ClientController(Connection connection, ConnectionMode connectionMode, PlayerData players) {
        this.connectionMode = connectionMode;
        this.connection = connection;
        this.players = players;
        this.status = Status.ONLINE;
        this.phase = Phase.LOGIN;
        this.username = "";
    }

    @Override
    public void run() {
        do {
            sendMessage("login<connection_established><Please login: >");
        } while (!login() && status == Status.ONLINE);
        if (status == Status.ONLINE) {
            System.out.println("User: " + username + " has signed in");
            nextPhase();
            Lobby.getLobby().joinLobby(this);
        }
    }

    private boolean login() {
        MessageReader messageReader;
        messageReader = getMessage();
        if (messageReader.hasNext()) {
            String tempUsername = messageReader.getNext();
            if (messageReader.hasNext()) {
                String password = messageReader.getNext();
                if (!messageReader.hasNext() && players.check(tempUsername, password)) {
                    sendMessage("login<true><Now you are logged>");
                    this.username = tempUsername;
                    return true;
                } else {
                    sendMessage("login<false><Username not available>");
                    return false;
                }
            }
        }
        sendMessage("login<invalid_command>");
        return false;
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

    public void sendMessage(String message) {
        if (status == Status.ONLINE)
            connection.sendMessage(message);
    }

    public MessageReader getMessage() {
        MessageReader messageReader;
        while (status == Status.ONLINE) {
            try {
                messageReader = new MessageReader(connection.getMessage());
            } catch (NullPointerException e) {
                connection.close();
                status = Status.OFFLINE;
                break;
            }
            if (messageReader.hasNext()) {
                String declaredTAG = messageReader.getNext();
                switch (declaredTAG) {
                    case "changeCommunicationMode":
                        changeConnectionMode();
                        break;
                    case "quit":
                        connection.close();
                        this.status = Status.OFFLINE;
                        break;
                    default:
                        if (getTag().equals(declaredTAG)) {
                            return messageReader;
                        } else {
                            sendMessage(getTag() + "<Invalid command>");
                        }
                }
            }
        }
        if (username.equals("")) {
            System.out.println("Client closed connection");
            close();
        } else {
            players.disconnect(username);
            System.out.println("User: " + username + " logged out");
            if (phase == Phase.GAME) {
                messageReader = new MessageReader("<end_turn>");
                return messageReader;
            }
        }
        messageReader = new MessageReader("quit");
        return messageReader;
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
        if(connection.isConnected())
            status = Status.ONLINE;
        else
            status = Status.OFFLINE;
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
}