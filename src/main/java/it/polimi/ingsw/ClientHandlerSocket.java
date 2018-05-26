package it.polimi.ingsw;

import it.polimi.ingsw.Server.ServerMain;
import it.polimi.ingsw.exceptions.NotValidInputException;
import static it.polimi.ingsw.connection.ConnectionMode.SOCKET;

public class ClientHandlerSocket {
    private PlayerDatabase playerDatabase;

    public ClientHandlerSocket() {
        this.playerDatabase = PlayerDatabase.getPlayerDatabase();
    }

    public void login(String username, String password, ClientSocketInterpreter client) throws NotValidInputException {
        System.out.println("Client number "+ ServerMain.getServerMain().getNewClientNumber()+" connected through Socket");
        if (playerDatabase.check(username, password, SOCKET)) {
            System.out.println("User: "+username+" logged in.");
            playerDatabase.addSocketClient(username, client);
        } else {
            throw new NotValidInputException();
        }
    }

    public void joinLobby(String username, long time) throws NotValidInputException {
        long systemTime = System.currentTimeMillis()/1000; //current unix time in seconds
        if (systemTime > time) {
            Lobby.getLobby().addPlayer(username, time);
        } else throw new NotValidInputException();
    }
}

