package it.polimi.ingsw.server.socket;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.model.cards.patterns.PatternDeck;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer extends Thread{

    private ServerSocket serverSocket;

    /**
     * creates a {@link SocketServer} given a {@link Integer} port.
     * @param port : the given {@link Integer} port
     * @throws NetworkErrorException if any connection related error occurs
     */
    public SocketServer(int port) throws NetworkErrorException {
        try {
            this.serverSocket = new java.net.ServerSocket(port);
        } catch (IOException e) {
            throw new NetworkErrorException();
        }
        System.out.println("SocketServer waiting for client on port " + serverSocket.getLocalPort());
        start();
    }

    /**
     * handles the creation of new {@link ClientSocketInterpreter}.
     */
    @Override
    public void run() {
        Logger logger = Logger.getLogger(PatternDeck.class.getName());
        Socket socket;
        try {
            while (Server.getStatus()) {
                socket = serverSocket.accept();
                Runnable client = new ClientSocketInterpreter(socket);
                new Thread(client).start();
            }
        } catch(Exception e) {
            logger.log( Level.SEVERE, e.toString(), e);
            try {
                serverSocket.close();
            } catch(NullPointerException | IOException ex) {
                logger.log( Level.SEVERE, ex.toString(), ex);
            }
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.log( Level.SEVERE, e.toString(), e);
            }
        }
    }

    /**
     * closes this {@link ServerSocket}.
     */
    public void close(){
        System.exit(0);
    }
}
