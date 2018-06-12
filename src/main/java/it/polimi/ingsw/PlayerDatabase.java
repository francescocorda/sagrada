package it.polimi.ingsw;

import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.connection.ConnectionMode;
import org.omg.CosNaming.NamingContextPackage.NotFound;
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

    public boolean check(String user, String password){
        if(stringCheck(user) && stringCheck(password)){
            for(PlayerData playerData : players)
                if(playerData.getUsername().equals(user))
                    if((playerData.getPassword().equals(password)) && !playerData.isConnected()){
                        playerData.changeStatus();
                        return true;
                    } else {
                        return false;
                    }
            players.add(new PlayerData(user, password));
            System.out.println("Connected players: " + onlinePlayersNumber());
            return true;
        } else {
            return false;
        }
    }

    public void addRMIClient(String username, RMIClientInterface client){
        PlayerData player = findPlayer(username);
        if(player != null){
            player.setClientRMI(client);
        }
    }

    public void removeRMIClient(String username){
        System.out.println("Client: " + username + " disconnected.");
        PlayerData player = findPlayer(username);
        phaseDisconnection(player);
    }

    public void addSocketClient(String username, ClientSocketInterpreter client){
        PlayerData player = findPlayer(username);
        if(player != null){
            player.setClientSocket(client);
        }
    }

    public void removeSocketClient(String username){  //it should be joined with removeRMIClient and phaseDisconnection
        System.out.println("Client: " + username + " disconnected.");
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
        PlayerData client = findPlayer(username);
        if(client == null)
            throw new NotFound();
        return client.getClientSocket();
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

    public void setPhase(String username , Phase phase){
        PlayerData playerData = findPlayer(username);
        if(playerData != null)
            playerData.setPhase(phase);
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

    private boolean stringCheck(String string){
        return (string.length()>0 && !string.equals(" "));
    }
}
