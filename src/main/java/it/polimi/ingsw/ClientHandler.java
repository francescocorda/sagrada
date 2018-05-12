package it.polimi.ingsw;

public class ClientHandler implements Runnable {

    private PlayerData players;
    private Connection connection;
    private ConnectionMode connectionMode;
    private String username;

    private enum Phase {
        LOGIN,
        LOBBY,
        GAME
    }

    private Phase phase;

    ClientHandler(Connection connection, ConnectionMode connectionMode, PlayerData players) {
        this.connection = connection;
        this.connectionMode = connectionMode;
        this.players = players;
        this.phase = Phase.LOGIN;
        username = "";
    }

    @Override
    public void run() {
        do {
            sendMessage("login<connection_established><Please login: >");
        } while (!login());
        this.phase = Phase.LOBBY;
        Lobby.getLobby().joinLobby(this);
    }

    private boolean login() {
        MessageReader messageReader;
        messageReader = getMessage();
        if (messageReader.hasNext()) {
            String tempUsername = messageReader.getNext();
            if (messageReader.hasNext()) {
                String password = messageReader.getNext();
                if (players.check(tempUsername, password)) {
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
    }

    public void sendMessage(String message) {
        connection.sendMessage(message);
    }

    public MessageReader getMessage() {
        while (true) {
            MessageReader messageReader = new MessageReader(connection.getMessage());
            if (messageReader.hasNext()) {
                String declaredTAG = messageReader.getNext();
                switch (declaredTAG) {
                    case "changeCommunicationMode":
                        changeConnectionMode();
                        break;
                    case "quit":
                        connection.close();
                        //DO WHAT HAPPENS WHEN A CLIENT QUIT
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
}