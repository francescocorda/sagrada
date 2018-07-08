package it.polimi.ingsw.database;

import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.server.client_handler.ClientHandler;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

import static it.polimi.ingsw.database.Phase.GAME;
import static it.polimi.ingsw.database.Phase.LOGIN;

public class ClientDatabase {
    private ArrayList<ClientData> players;
    private static ClientDatabase instance;

    /**
     * @return the only instance of {@link ClientDatabase}.
     */
    public static synchronized ClientDatabase getPlayerDatabase() {
        if (instance == null) {
            instance = new ClientDatabase();
        }
        return instance;
    }

    /**
     * create an instance of {@link ClientDatabase}.
     */
    private ClientDatabase() {
        players = new ArrayList<>();
    }

    /**
     * checks if given {@link String}s username and password are valid or not.
     * it also creates a {@link ClientData} if there's no one in {@link #players} with such username
     * @param user : the given {@link String} username
     * @param password : the given {@link String} password
     * @return true if the given parameters are correct or if a new {@link ClientData} is created on purpose;
     * otherwise the returned value is false
     */
    public boolean check(String user, String password) {
        if (stringCheck(user) && stringCheck(password)) {
            for (ClientData clientData : players)
                if (clientData.getUsername().equals(user))
                    if ((clientData.getPassword().equals(password)) && !clientData.isConnected()) {
                        clientData.changeStatus();
                        if (clientData.getPhase() != GAME)
                            clientData.setPhase(LOGIN);
                        return true;
                    } else {
                        return false;
                    }
            players.add(new ClientData(user, password));
            System.out.println("Connected players: " + onlinePlayersNumber());
            return true;
        } else {
            return false;
        }
    }

    /**
     * shows the status of {@link ClientDatabase}
     */
    public void status() {
        System.out.println("CLIENT DATABASE:");
        System.out.println(" -Players number: " + players.size());
        System.out.println(" -Online players number: " + onlinePlayersNumber());
        System.out.println(" -Players:");
        for (ClientData player : players) {
            System.out.println("  + " + player.getUsername() + " - " + (player.isConnected() ?
                    player.getPhase() : "OFFLINE"));
        }
    }

    /**
     *find a {@link ClientData} corresponding to the given {@link String} username and sets it's
     * {@link ClientData#clientHandler} as the given one.
     * @param username : the given {@link String} username
     * @param clientHandler : the given {@link ClientHandler}
     */
    public void setClientHandler(String username, ClientHandler clientHandler) {
        ClientData player = getPlayerData(username);
        if (player != null) {
            player.setClientHandler(clientHandler);
        }
    }

    /**
     * disconnects the client corresponding to the given {@link String} username.
     * @param username : the given {@link String} username
     */
    public void disconnect(String username) {
        ClientData toBeDisconnected = null;
        for (ClientData clientData : players) {
            if (clientData.getUsername().equals(username) && clientData.isConnected()) {
                toBeDisconnected = clientData;
            }
        }
        if (toBeDisconnected != null) {
            toBeDisconnected.shutDownTimer();
            toBeDisconnected.changeStatus();
            phaseDisconnection(toBeDisconnected);
        }
    }

    /**
     * @return the number of {@link #players} of which {@link ClientData#status} is {@link Status#ONLINE}
     */
    private int onlinePlayersNumber() {
        int number = 0;
        for (ClientData clientData : players) {
            if (clientData.isConnected()) {
                number++;
            }
        }
        return number;
    }

    /**
     * @param username : the given {@link String} username
     * @return {@link ClientData} who's {@link ClientData#username} corresponds to the given {@link String} username
     */
    public ClientData getPlayerData(String username) {
        for (ClientData c : players) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    /**
     * @param username : the given {@link String} username
     * @return {@link ClientData#lastTime} who's {@link ClientData#username}
     * corresponds to the given {@link String} username
     */
    public long getPlayerLastTime(String username){
        ClientData clientData = getPlayerData(username);
        if (clientData != null)
            return clientData.getLastTime();
        return 0;
    }

    /**
     * sets as the given {@link Long} lastTime {@link ClientData#lastTime} who's {@link ClientData#username}
     * corresponds to the given {@link String} username.
     * @param username : the given {@link String} username
     * @param lastTime : the given {@link Long} lastTime
     */
    public void setPlayerLastTime(String username, long lastTime){
        ClientData clientData = getPlayerData(username);
        if (clientData != null)
            clientData.setLastTime(lastTime);
    }

    /**
     * disconnects the given {@link ClientData} player according to it's {@link Phase}.
     * @param player : the given {@link ClientData} player
     */
    private void phaseDisconnection(ClientData player) {
        switch (player.getPhase()) {
            case GAME:
                VirtualView virtualView = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(player.getUsername());
                virtualView.notifyObservers(player.getUsername() + "/logout");
                System.out.println("Game: player: " + player.getUsername() + " left the game...");
                break;
            case LOBBY:
                Lobby.getLobby().removePlayer(player.getUsername());
                break;
            default:
                VirtualView view = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(player.getUsername());
                if (view != null)
                    VirtualViewsDataBase.getVirtualViewsDataBase().removeVirtualView(player.getUsername());
                break;
        }
        player.getClientHandler().close();
    }

    /**
     * checks if the given {@link String} string has a valid value.
     * @param string : the given {@link String} string
     * @return true if the given {@link String} has a valid value; false otherwise
     */
    private boolean stringCheck(String string) {
        return (string.length() > 0 && !string.equals(" "));
    }
}
