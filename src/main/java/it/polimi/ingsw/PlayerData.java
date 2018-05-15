package it.polimi.ingsw;

import java.util.ArrayList;

public class PlayerData{
    private ArrayList<Data> players;

    public PlayerData() {
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


class Data {
    private String username;
    private String password;
    private boolean isConnected;

    Data(String username, String password){
        this.username=username;
        this.password=password;
        this.isConnected=true;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void changeStatus(){
        isConnected = !isConnected;
    }
}
