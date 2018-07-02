package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.util.Timer;
import java.util.TimerTask;
import static it.polimi.ingsw.Phase.*;

public class ClientData {

    private String username;
    private String password;
    private Phase phase;
    private Status status;
    private ClientHandler clientHandler;
    private Timer timer;

    public ClientData(String username, String password){
        this.username = username;
        this.password = password;
        this.phase = Phase.LOGIN;
        this.status = Status.ONLINE;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    clientHandler.check();
                } catch (NetworkErrorException e) {
                    System.out.println("PERIODIC CHECK TRIGGERED");
                    System.out.println("1");
                    ClientDatabase.getPlayerDatabase().disconnect(username);
                    this.cancel();
                }
            }
        }, 0, 1000);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Phase getPhase() {
        return this.phase;
    }

    public void setPhase(Phase phase){
        this.phase=phase;
    }

    public void nextPhase() {
        switch (phase) {
            case GAME:
                phase = END_GAME;
                break;
            case LOBBY:
                phase = GAME;
                break;
            case LOGIN:
                phase = LOBBY;
                break;
            case END_GAME:
                phase = LOGIN;
                break;
            default:
                phase = LOGIN;
                break;
        }
    }

    public boolean isConnected(){
        return status == Status.ONLINE;
    }

    public void changeStatus() {
        if (this.status == Status.ONLINE)
            this.status = Status.OFFLINE;
        else
            this.status = Status.ONLINE;
    }
}