package it.polimi.ingsw.connection;

import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.Model.Game.Color;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionSocket {
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private Socket socket;
    private final Logger LOGGER = Logger.getLogger(PatternDeck.class.getName());

    public ConnectionSocket(Socket socket) {
        this.socket = socket;
        connectionSocketHandler();
    }

    /**
     * initialises all the fields to handle socket communication.
     */
    private void connectionSocketHandler() {
        try {
            //setup communication channels
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);

            try {
                socket.close();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    /**
     * sends a message through {@link #outSocket}.
     *
     * @param message to be sent through {@link #outSocket}.
     */
    public void sendMessage(String message) {
        outSocket.println(message);
       /* if (!message.equals("pong") && !message.equals("ping"))
            System.out.println(Color.RED.escape() + "MESSAGE OUT: " + message + Color.RESET);*/
    }

    /**
     * gets a message from {@link #inSocket}.
     *
     * @return null if method {@link BufferedReader#readLine()} of {@link #inSocket}
     * throws an {@link IOException} otherwise it returns the received message
     * if it's correctly received.
     */
    public String getMessage() {
        String message;
        try {
            message = inSocket.readLine();
            /*if (!message.equals("ping") && !message.equals("pong"))
                System.out.println(Color.YELLOW.escape() + "MESSAGE IN: " + message + Color.RESET);*/
        } catch (IOException e) {
            message = null;
        }
        return message;
    }

    /**
     * closes socket communication channel.
     * It does that by calling {@link Socket#close()} of {@link #socket} field and
     * if a {@link IOException} is thrown it prints the error LOG.
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
