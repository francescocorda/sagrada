package it.polimi.ingsw.connection;

import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;

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

    private void connectionSocketHandler(){
        try {
            //setup communication channels
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (Exception e) {
            LOGGER.log( Level.SEVERE, e.toString(), e);

            try {
                socket.close();
            } catch (Exception ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    public void sendMessage(String message){
        outSocket.println(message);
    }

    public String getMessage(){
        String message;
        try {
            message=inSocket.readLine();
        } catch (IOException e) {
            message=null;
        }
        return message;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.log( Level.SEVERE, e.toString(), e);
        }
    }
}
