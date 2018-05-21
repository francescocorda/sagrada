package it.polimi.ingsw;

public class Data {
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