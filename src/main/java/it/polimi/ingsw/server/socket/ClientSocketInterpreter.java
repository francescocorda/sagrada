package it.polimi.ingsw.server.socket;

import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.database.Status;
import it.polimi.ingsw.server.client_handler.ClientHandlerSocket;
import it.polimi.ingsw.database.ClientData;
import it.polimi.ingsw.database.ClientDatabase;
import it.polimi.ingsw.database.VirtualViewsDataBase;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.VirtualView;

import static it.polimi.ingsw.database.Status.*;


import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientSocketInterpreter implements Runnable, Observer {

    private ConnectionSocket connection;
    private ClientDatabase players;
    private Status status;
    private String username;
    private SocketReader reader = null;

    /**
     * creates a new {@link ClientSocketInterpreter} given a {@link Socket} socket.
     * @param socket : the given {@link Socket}
     */
    ClientSocketInterpreter(Socket socket) {
        this.connection = new ConnectionSocket(socket);
        players = ClientDatabase.getPlayerDatabase();
        status = ONLINE;
    }

    /**
     * handles socket player.
     */
    @Override
    public void run() {
        login();
        sendMessage("lobby/last_access/insert_last_access");
    }

    /**
     * handles socket player's login.
     */
    private void login() {
        ArrayList<String> commands;
        connection.sendMessage("login/insert_credentials");
        while (status == ONLINE) {
            commands = messageParser(connection.getMessage());
            if (commands.size() == 3 && commands.remove(0).equals("login")) {
                String tempUsername = commands.remove(0);
                if (!(tempUsername.equals("") || commands.isEmpty())) {
                    String password = commands.remove(0);
                    if (!(password.equals("") || !commands.isEmpty())) {
                        try {
                            loginHandler(tempUsername, password, this);
                            connection.sendMessage("login/success");
                            this.username = tempUsername;
                            return;
                        } catch (NotValidInputException e) {
                            connection.sendMessage("login/failed");
                        }
                    } else {
                        connection.sendMessage("login/failed");
                    }
                } else {
                    connection.sendMessage("login/invalid_command");
                }
            } else
                connection.sendMessage("login/invalid_command");
            connection.sendMessage("login/insert_credentials");
        }
    }

    /**
     * handle player's joining lobby phase given an {@link ArrayList<String>} commands.
     * @param commands : the given {@link ArrayList<String>}
     */
    private void joinLobby(List<String> commands) {
        final String invalidCommand = "lobby/invalid_command";
        long systemTime = System.currentTimeMillis() / 1000; //current unix time in seconds
        String tempMessage;
        while (status == ONLINE) {
            if (!commands.isEmpty()) {
                tempMessage = commands.remove(0);
                if (tempMessage.equals("last_access")) {
                    if (!commands.isEmpty()) {
                        String timeString = commands.remove(0);
                        boolean isValid = true;
                        long time = 0;
                        try {
                            time = Long.parseLong(timeString);
                        } catch (NumberFormatException e) {
                            isValid = false;
                        }
                        if (isValid && systemTime > time) {
                            try {
                                joinLobbyHandler(username, time);
                                break;
                            } catch (NotValidInputException e) {
                                sendMessage(invalidCommand);
                            }
                        } else {
                            sendMessage("lobby/last_access/invalid_time");
                        }
                    } else {
                        sendMessage(invalidCommand);
                    }
                } else {
                    if (tempMessage.equals("exit"))
                        break;
                    else {
                        sendMessage(invalidCommand);
                    }
                }
            } else {
                sendMessage(invalidCommand);
            }
        }
    }

    /**
     * sends the given {@link String} message to the client.
     * it does that through the use of method {@link ConnectionSocket#sendMessage(String)}
     * @param message : the given {@link String} message
     */
    public void sendMessage(String message) {
        if (isOnline()) {
            connection.sendMessage(message);
        }
    }

    /**
     * @return {@link #username}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * closes this {@link ClientSocketInterpreter}.
     */
    public void close() {
        this.connection.close();
        if (reader != null)
            reader.kill();
        VirtualView temp = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(username);
        if (temp != null)
            temp.deleteObserver(this);
        Thread.currentThread().interrupt();
    }

    /**
     * @return true if the client is still {@link Status#ONLINE}.
     */
    public boolean isOnline() {
        if (status == ONLINE) {
            reader.waitForPong();
            return true;
        } else {
            reader.kill();
            VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(username).deleteObserver(this);
            return false;
        }
    }

    /**
     * set {@link #status} to {@link Status#OFFLINE}.
     */
    public void offline() {
        status = OFFLINE;
    }

    /**
     * handles player's login action given it's credentials.
     * @param username : the given {@link String} username
     * @param password : the given {@link String} password
     * @param client : the given {@link ClientSocketInterpreter} client
     * @throws NotValidInputException if given credentials are not valid
     */
    private void loginHandler(String username, String password, ClientSocketInterpreter client) throws NotValidInputException {
        toScreen("Client number " + Server.getServerMain().getNewClientNumber() + " connected through Socket");
        if (players.check(username, password)) {
            toScreen("User: " + username + " logged in.");
            ClientData player = ClientDatabase.getPlayerDatabase().getPlayerData(username);
            if (VirtualViewsDataBase.getVirtualViewsDataBase().contains(username)) {
                VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(username).addObserver(this);
            } else {
                VirtualView view = new VirtualView(player);
                view.addObserver(this);
                VirtualViewsDataBase.getVirtualViewsDataBase().addVirtualView(view);
            }
            reader = new SocketReader(connection, username, this);
            players.setClientHandler(username, new ClientHandlerSocket(client));
        } else {
            throw new NotValidInputException();
        }
    }

    /**
     * handles player's joinLobby action given it's username and time.
     * @param username : the given {@link String} username
     * @param time : the given {@link Long} time
     * @throws NotValidInputException if the given values are not valid
     */
    private void joinLobbyHandler(String username, long time) throws NotValidInputException {
        long systemTime = System.currentTimeMillis() / 1000; //current unix time in seconds
        if (systemTime > time) {
            Lobby.getLobby().addPlayer(username, time);
        } else throw new NotValidInputException();
    }

    /**
     * gets the {@link String} message to be handled by {@link #handleEvent(String)}.
     * @param message : the given {@link String} message
     */
    @Override
    public void update(String message) {
        if (message != null)
            handleEvent(message);
    }

    /**
     * it does nothing.
     * @param o : the given {@link Observable}
     */
    @Override
    public void update(Observable o) {

    }

    /**
     * handles the given message.
     * @param message : the given {@link String} message
     */
    private void handleEvent(String message) {
        ArrayList<String> commands = messageParser(message);
        if (message.equals("logout"))
            players.disconnect(username);
        if (commands.size() > 2 && commands.remove(0).equals(username) &&
                commands.remove(0).equals("lobby")) {
            joinLobby(commands);
        }
    }

    /**
     * converts a given {@link String} message into it's corresponding {@link ArrayList<String>} of commands.
     * @param message : the given {@link String} message
     * @return an {@link ArrayList<String>} of commands that derives from
     * the conversion of given {@link String} message
     */
    private ArrayList<String> messageParser(String message) {
        return new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
    }

    /**
     * displays the given {@link String} message.
     * @param message : the given {@link String} message
     */
    private void toScreen(String message) {
        System.out.println(message);
    }
}
