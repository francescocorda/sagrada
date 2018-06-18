package it.polimi.ingsw;

import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

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

    public boolean check(String user, String password){
        if(stringCheck(user) && stringCheck(password)){
            for(ClientData clientData : players)
                if(clientData.getUsername().equals(user))
                    if((clientData.getPassword().equals(password)) && !clientData.isConnected()){
                        clientData.changeStatus();
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

    public void setClientHandler(String username, ClientHandler clientHandler){
        ClientData player = findPlayer(username);
        if(player != null){
            player.setClientHandler(clientHandler);
        }
    }

    public void disconnect(String username){
        ClientData toBeDisconnected = null;
        for(ClientData clientData : players){
            if(clientData.getUsername().equals(username) && clientData.isConnected()){
                toBeDisconnected = clientData;
                clientData.changeStatus();
            }
        }
        if(toBeDisconnected != null)
            phaseDisconnection(toBeDisconnected);
    }

    public boolean contain(String user){
        for(ClientData clientData : players){
            if(clientData.getUsername().equals(user)){
                return true;
            }
        }
        return false;
    }

    public int onlinePlayersNumber(){
        int number=0;
        for(ClientData clientData : players){
            if(clientData.isConnected()){
                number++;
            }
        }
        return  number;
    }

    public void setPhase(String username , Phase phase){
        ClientData clientData = findPlayer(username);
        if(clientData != null)
            clientData.setPhase(phase);
    }

    public Phase getPhase(String username){
        ClientData clientData = findPlayer(username);
        if(clientData != null)
            return clientData.getPhase();
        return null;
    }

    public ClientData findPlayer(String username){
        for(ClientData c : players){
            if(c.getUsername().equals(username)){
                return c;
            }
        }
        return null;
    }

    public ClientData getPlayerData(String username){
        return findPlayer(username);
    }

    private void phaseDisconnection(ClientData player){
        switch(player.getPhase()){
            case GAME:
                VirtualView virtualView = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(player.getUsername());
                virtualView.notifyObservers(player.getUsername()+"/exit");
                break;
            case LOBBY:
                Lobby.getLobby().removePlayer(player.getUsername());
                break;
            default:
                break;
        }
    }

    private boolean stringCheck(String string){
        return (string.length()>0 && !string.equals(" "));
    }
}
