package it.polimi.ingsw;

import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.connection.ConnectionMode;
import static it.polimi.ingsw.Phase.*;

public class ClientData {

    private String username;
    private String password;
    private Phase phase;
    private Status status;
    private ClientHandler clientHandler;

    public ClientData(String username, String password){
        this.username = username;
        this.password = password;
        this.phase = Phase.LOGIN;
        this.status = Status.ONLINE;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientRMI(RMIClientInterface clientRMI) {
        this.clientHandler = new ClientHandlerRMI(clientRMI);
    }

    public void setClientSocket(ClientSocketInterpreter clientSocket){
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

    public void changeStatus() {
        if (this.status == Status.ONLINE)
            this.status = Status.OFFLINE;
        else
            this.status = Status.ONLINE;
    }
}