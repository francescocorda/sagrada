package it.polimi.ingsw;

import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.connection.ConnectionMode;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerDatabase {
    private ArrayList<PlayerData> players;
    private HashMap<String, ClientSocketInterpreter> clientsSocket;

    private static PlayerDatabase instance;

    public static synchronized PlayerDatabase getPlayerDatabase() {
        if (instance == null) {
            instance = new PlayerDatabase();
        }
        return instance;
    }

    private PlayerDatabase() {
        players = new ArrayList<>();
        clientsSocket = new HashMap<>();
    }

    public boolean check(String user, String password, ConnectionMode connectionMode){
        for(PlayerData playerData : players)
            if(playerData.getUsername().equals(user))
                if((playerData.getPassword().equals(password)) && !playerData.isConnected()){
                    playerData.changeStatus();
                    playerData.setConnectionMode(connectionMode);
                    return true;
                } else {
                    return false;
                }
        players.add(new PlayerData(user, password, connectionMode));
        System.out.println("Connected players: " + onlinePlayersNumber());
        return true;
    }

    public void addRMIClient(String username, RMIClientInterface client){
        PlayerData player = findPlayer(username);
        if(player != null && player.getCurrentConnectionMode() == ConnectionMode.RMI){
            player.setClientRMI(client);
        }
    }

    public void removeRMIClient(String username){
        System.out.println("Client: " + username + " disconnected.");
        PlayerData player = findPlayer(username);
        phaseDisconnection(player);
    }

    public void addSocketClient(String username, ClientSocketInterpreter client){
        if(clientsSocket==null){
            clientsSocket.put(username, client);
        } else if(!clientsSocket.containsKey(username)){
            clientsSocket.put(username, client);
        }
    }

    public void removeSocketClient(String username){
        System.out.println("Client: " + username + " disconnected.");
        clientsSocket.remove(username);
        PlayerData player = findPlayer(username);
        phaseDisconnection(player);
    }

    public RMIClientInterface getClientRMI(String username) throws NotFound{
        PlayerData client = findPlayer(username);
        if(client == null)
            throw new NotFound();
        return client.getClientRMI();
    }

    public ClientSocketInterpreter getClientSocket(String username) throws NotFound{
        ClientSocketInterpreter client = clientsSocket.get(username);
        if(client == null)
            throw new NotFound();
        return client;
    }

    public void disconnect(String username){
        for(PlayerData playerData : players){
            if(playerData.getUsername().equals(username) && playerData.isConnected())
                    playerData.changeStatus();
            }
    }

    public boolean contain(String user){
        for(PlayerData playerData : players){
            if(playerData.getUsername().equals(user)){
                return true;
            }
        }
        return false;
    }

    public int onlinePlayersNumber(){
        int number=0;
        for(PlayerData playerData : players){
            if(playerData.isConnected()){
                number++;
            }
        }
        return  number;
    }

    public void nextPhase(String username){
        PlayerData playerData = findPlayer(username);
        if(playerData != null)
            playerData.nextPhase();
    }

    public void setPhase(String username , Phase phase){
        PlayerData playerData = findPlayer(username);
        if(playerData != null)
            playerData.setPhase(phase);
    }

    public int sizeLobby(){
        int count=0;
        for(PlayerData c : players) if(c.getPhase().equals(Phase.LOBBY)) count++;
        return count;
    }

    public ArrayList<PlayerData> getGamePlayers() {
        ArrayList<PlayerData> lobbyPlayers = new ArrayList<>();
        for(PlayerData c : players) {
            if (c.getPhase().equals(Phase.LOBBY)) {
                lobbyPlayers.add(c);
                c.setPhase(Phase.GAME);
            }
        }
        return lobbyPlayers;
    }

    public Phase getPhase(String username){
        PlayerData playerData = findPlayer(username);
        if(playerData != null)
            return playerData.getPhase();
        return null;
    }

    public PlayerData findPlayer(String username){
        for(PlayerData c : players){
            if(c.getUsername().equals(username)){
                return c;
            }
        }
        return null;
    }

    public PlayerData getPlayerData(String username){
        return findPlayer(username);
    }

    private void phaseDisconnection(PlayerData player){
        switch(player.getPhase()){
            case GAME:
                break;
            case LOBBY:
                Lobby.getLobby().removePlayer(player);
                disconnect(player.getUsername());
                break;
            default:
                disconnect(player.getUsername());
        }
    }
}
