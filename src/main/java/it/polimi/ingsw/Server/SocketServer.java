package it.polimi.ingsw.Server;

import it.polimi.ingsw.ClientHandlerSocket;
import it.polimi.ingsw.ClientSocketInterpreter;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer {

    private int PORT;
    private ClientHandlerSocket handler;
    private ServerSocket serverSocket;
    private final Logger LOGGER = Logger.getLogger(PatternDeck.class.getName());


    SocketServer(int PORT)
    {
        this.PORT = PORT;
        this.handler = new ClientHandlerSocket();
        serverSocket = null;
        Socket socket;
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
                Runnable client = new ClientSocketInterpreter(socket, handler);
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
