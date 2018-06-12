package it.polimi.ingsw;

import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.connection.ConnectionMode;
import static it.polimi.ingsw.Phase.*;

public class PlayerData {

    private ConnectionMode connectionMode;
    private String username;
    private String password;
    private Phase phase;
    private Status status;
    private RMIClientInterface clientRMI;
    private ClientSocketInterpreter clientSocket;
    private ClientHandler clientHandler;

    public PlayerData(String username, String password){
        this.username=username;
        this.password=password;
        this.phase= Phase.LOGIN;
        this.status= Status.ONLINE;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public RMIClientInterface getClientRMI() {
        return clientRMI;
    }

    public void setClientRMI(RMIClientInterface clientRMI) {
        this.connectionMode = ConnectionMode.RMI;
        this.clientHandler = new ClientHandlerRMI(clientRMI);
    }

    public ClientSocketInterpreter getClientSocket(){
        return clientSocket;
    }

    public void setClientSocket(ClientSocketInterpreter clientSocket){
        this.connectionMode = ConnectionMode.SOCKET;
        this.clientHandler = new ClientHandlerSocket(clientSocket);
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

    public ConnectionMode getCurrentConnectionMode() {
        return connectionMode;
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
        if(this.status==Status.ONLINE)
            return true;
        else
            return false;
    }

    public void setConnectionMode(ConnectionMode connectionMode){
        this.connectionMode = connectionMode;
    }

    public void changeStatus() {
        if (this.status == Status.ONLINE)
            this.status = Status.OFFLINE;
        else
            this.status = Status.ONLINE;
    }
}