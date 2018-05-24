package it.polimi.ingsw;

import it.polimi.ingsw.Server.ServerMain;
import it.polimi.ingsw.exceptions.NotValidInputException;

import static it.polimi.ingsw.connection.ConnectionMode.SOCKET;

public class ClientHandlerSocket implements ClientHandlerInterface {
    private PlayerDatabase playerDatabase;

    public ClientHandlerSocket() {
        this.playerDatabase = PlayerDatabase.getPlayerDatabase();
    }

    @Override
    public void login(String username, String password) throws NotValidInputException {
        System.out.println("Client number "+ ServerMain.getServerMain().getNewClientNumber()+" connected through Socket");
        if (playerDatabase.check(username, password, SOCKET)) {
            System.out.println("User: "+username+" logged in.");
        } else {
            throw new NotValidInputException();
        }
    }

    @Override
    public void joinLobby(String username, long time) throws NotValidInputException {
        long systemTime = System.currentTimeMillis()/1000; //current unix time in seconds
        if (systemTime > time) {
            Lobby.getLobby().addPlayer(username, time);
        } else throw new NotValidInputException();
    }
}

