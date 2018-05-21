package it.polimi.ingsw;

import java.util.ArrayList;

public class PlayerData{
    private ArrayList<Data> players;
    private static PlayerData instance;

    public static synchronized PlayerData getPlayerData() {
        if (instance == null) {
            instance = new PlayerData();
        }
        return instance;
    }

    private PlayerData() {
        players = new ArrayList<>();
    }

    public boolean check(String user, String password){
        for(Data data : players)
            if(data.getUsername().equals(user))
                if((data.getPassword().equals(password)) && !data.isConnected()){
                    data.changeStatus();
                    return true;
                } else {
                    return false;
                }
        players.add(new Data(user, password));
        return true;
    }

    public void disconnect(String username){
        for(Data data: players){
            if(data.getUsername().equals(username)){
                data.changeStatus();
            }
        }
    }

    public boolean contain(String user){
        for(Data data: players){
            if(data.getUsername().equals(user)){
                return true;
            }
        }
        return false;
    }

    public int onlinePlayersNumber(){
        int number=0;
        for(Data data: players){
            if(data.isConnected()){
                number++;
            }
        }
        return  number;
    }
}
