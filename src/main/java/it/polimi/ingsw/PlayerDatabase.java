package it.polimi.ingsw;

import it.polimi.ingsw.connection.ConnectionMode;

import java.util.ArrayList;

public class PlayerDatabase {
    private ArrayList<PlayerData> players;
    private static PlayerDatabase instance;

    public static synchronized PlayerDatabase getPlayerDatabase() {
        if (instance == null) {
            instance = new PlayerDatabase();
        }
        return instance;
    }

    private PlayerDatabase() {
        players = new ArrayList<>();
    }

    public boolean check(String user, String password, ConnectionMode connectionMode){
        for(PlayerData playerData : players)
            if(playerData.getUsername().equals(user))
                if((playerData.getPassword().equals(password)) && !playerData.isConnected()){
                    playerData.changeStatus();
                    return true;
                } else {
                    return false;
                }
        players.add(new PlayerData(user, password, connectionMode));
        return true;
    }

    public void disconnect(String username){
        for(PlayerData playerData : players){
            if(playerData.getUsername().equals(username)){
                playerData.changeStatus();
            }
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

    private PlayerData findPlayer(String username){
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
}
