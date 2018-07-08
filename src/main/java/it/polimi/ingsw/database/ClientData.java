package it.polimi.ingsw.database;

import it.polimi.ingsw.server.client_handler.ClientHandler;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.util.Timer;
import java.util.TimerTask;
import static it.polimi.ingsw.database.Phase.*;

public class ClientData {

    private String username;
    private String password;
    private Phase phase;
    private Status status;
    private ClientHandler clientHandler;
    private Timer timer;
    private long lastTime;

    /**
     * creates a {@link ClientData} given a {@link String} username and a {@link String} password.
     * it also sets {@link #phase} to {@link Phase#LOGIN} and {@link #status} to {@link Status#ONLINE}
     * @param username : player's username
     * @param password : player's password
     */
    ClientData(String username, String password){
        this.lastTime = 0;
        this.username = username;
        this.password = password;
        this.phase = Phase.LOGIN;
        this.status = Status.ONLINE;
    }

    /**
     * @return this {@link ClientData#clientHandler}
     */
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    /**
     * sets {@link #clientHandler} as the given {@link ClientHandler}.
     * it also starts a periodic {@link Timer} that checks every second if client si online
     * @param clientHandler : the given {@link ClientHandler}
     */
    public void setClientHandler(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    clientHandler.check();
                } catch (NetworkErrorException e) {
                    ClientDatabase.getPlayerDatabase().disconnect(username);
                    this.cancel();
                }
            }
        }, 0, 1000);
    }

    /**
     * @return {@link #password}
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return {@link #username}
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return {@link #phase}
     */
    public Phase getPhase() {
        return this.phase;
    }

    /**
     * sets {@link #phase} as the given {@link Phase}.
     * @param phase : the given {@link Phase}
     */
    public void setPhase(Phase phase){
        this.phase=phase;
    }

    /**
     * @return {@link #lastTime}
     */
    public long getLastTime() {
        return lastTime;
    }

    /**
     * sets {@link #lastTime} as the given {@link Long} lastTime.
     * @param lastTime : is the given {@link Long} lastTime
     */
    public void setLastTime(long lastTime) {
        if(lastTime>=0)
            this.lastTime = lastTime;
    }

    /**
     * stets {@link #phase} to it's next value according to:
     * LOGIN     -> LOBBY
     * LOBBY     -> GAME
     * GAME      -> END_GAME
     * END_GAME  -> LOGIN
     */
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

    /**
     * @return if client is connected or not.
     */
    public boolean isConnected(){
        return status == Status.ONLINE;
    }

    /**
     * change {@link #status} according to:
     * ONLINE    -> OFFLINE
     * OFFLINE   -> ONLINE
     */
    public void changeStatus() {
        if (this.status == Status.ONLINE)
            this.status = Status.OFFLINE;
        else
            this.status = Status.ONLINE;
    }

    /**
     * shuts down the {@link Timer} that periodically checks client status.
     */
    public void shutDownTimer(){
        timer.cancel();
        timer.purge();
        timer = new Timer();
    }
}