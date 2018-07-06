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

    public static synchronized ClientDatabase getPlayerDatabase() {
        if (instance == null) {
            instance = new ClientDatabase();
        }
        return instance;
    }

    private ClientDatabase() {
        players = new ArrayList<>();
    }

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

    public void status() {
        //TODO eliminate
        System.out.println("CLIENT DATABASE:");
        System.out.println(" -Players number: " + players.size());
        System.out.println(" -Online players number: " + onlinePlayersNumber());
        System.out.println(" -Players:");
        for (ClientData player : players) {
            System.out.println("  + " + player.getUsername() + " - " + (player.isConnected() ?
                    player.getPhase() : "OFFLINE"));
        }
    }

    public void setClientHandler(String username, ClientHandler clientHandler) {
        ClientData player = findPlayer(username);
        if (player != null) {
            player.setClientHandler(clientHandler);
        }
    }

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

    public boolean contain(String user) {
        for (ClientData clientData : players) {
            if (clientData.getUsername().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public int onlinePlayersNumber() {
        int number = 0;
        for (ClientData clientData : players) {
            if (clientData.isConnected()) {
                number++;
            }
        }
        return number;
    }

    public void setPhase(String username, Phase phase) {
        ClientData clientData = findPlayer(username);
        if (clientData != null)
            clientData.setPhase(phase);
    }

    public Phase getPhase(String username) {
        ClientData clientData = findPlayer(username);
        if (clientData != null)
            return clientData.getPhase();
        return null;
    }

    public ClientData findPlayer(String username) {
        for (ClientData c : players) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    public long getPlayerLastTime(String username){
        ClientData clientData = findPlayer(username);
        if (clientData != null)
            return clientData.getLastTime();
        return 0;
    }

    public void setPlayerLastTime(String username, long lastTime){
        ClientData clientData = findPlayer(username);
        if (clientData != null)
            clientData.setLastTime(lastTime);
    }

    public ClientData getPlayerData(String username) {
        return findPlayer(username);
    }

    private void phaseDisconnection(ClientData player) {
        switch (player.getPhase()) {
            case GAME:
                VirtualView virtualView = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(player.getUsername());
                virtualView.notifyObservers(player.getUsername() + "/exit");
                System.out.println("game: player: " + player.getUsername() + " left the game...");
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

    private boolean stringCheck(String string) {
        return (string.length() > 0 && !string.equals(" "));
    }
}
