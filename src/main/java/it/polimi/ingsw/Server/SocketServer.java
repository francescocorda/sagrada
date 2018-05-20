package it.polimi.ingsw.Server;

import it.polimi.ingsw.ClientController;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.PlayerData;
import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.ConnectionMode;
import it.polimi.ingsw.connection.ConnectionSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer {

    private static int PORT;
    private PlayerData players;
    private ServerSocket serverSocket;
    private int clientCounter=0;
    private final Logger LOGGER = Logger.getLogger(PatternDeck.class.getName());


    SocketServer(int PORT)
    {
        this.PORT = PORT;
        serverSocket = null;
        Socket socket;
        players = new PlayerData();
        try {
            serverSocket = new java.net.ServerSocket(PORT);
        } catch (IOException e) {
            LOGGER.log( Level.SEVERE, e.toString(), e);
        }
        try {
            System.out.println("\nSocketServer waiting for client on port " + serverSocket.getLocalPort());

            // server infinite loop
            while (ServerMain.getStatus()) {
                socket = serverSocket.accept();
                clientCounter++;
                System.out.println("\nclient number: " + clientCounter + " has connected through Socket");
                System.out.println("Connected players: "+players.onlinePlayersNumber());
                Connection connection = new ConnectionSocket(socket);
                Runnable client = new ClientController(connection , ConnectionMode.SOCKET, players);
                new Thread(client).start();
            }
        } catch(Exception e) {
            LOGGER.log( Level.SEVERE, e.toString(), e);
            try {
                serverSocket.close();
            } catch(NullPointerException | IOException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex);
            }
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                LOGGER.log( Level.SEVERE, e.toString(), e);
            }
        }
    }
}
