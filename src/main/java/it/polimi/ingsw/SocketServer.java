package it.polimi.ingsw;

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
                System.out.println("\nClient number: " + clientCounter + " has connected");
                System.out.println("Players connected: "+players.onlinePlayersNumber());
                Connection connection = new ConnectionSocket(socket);
                Runnable client = new ClientController(connection , ConnectionMode.SOCKET, players);
                new Thread(client).start();
            }
        } catch(Exception e) {
            System.out.println(e);
            try {
                serverSocket.close();
            } catch(NullPointerException ex) {
                System.out.println(ex);
            } catch ( IOException ioe){
                System.out.println(ioe);
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
