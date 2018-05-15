package it.polimi.ingsw.Server;

import it.polimi.ingsw.Lobby;
import it.polimi.ingsw.Model.Game.Color;
import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.DiceBag;

public class ServerMain {

    private static boolean SERVER_UP;
    private static SocketServer serverSoket;
    private static int socketServerPort;

    public static void main( String[] args ) {

        //Initialize all the structure for the game/server
        SERVER_UP=true;
        socketServerPort=3000;
        Lobby.getLobby();
        start();
    }


    private static void start(){
        serverSoket = new SocketServer(socketServerPort);
    }

    public static boolean getStatus(){
        return SERVER_UP;
    }
}
