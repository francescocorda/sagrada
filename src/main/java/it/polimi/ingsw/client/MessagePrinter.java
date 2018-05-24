package it.polimi.ingsw.client;

import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.ConnectionSocket;

public class MessagePrinter extends Thread {

    private ConnectionSocket connection;

    public MessagePrinter(ConnectionSocket connection) {
        this.connection = connection;
        start();
    }

    @Override
    public void run () {
        boolean loop = true;
        while ( loop) {

            String message = connection.getMessage();
            if ( message == null ) {
                loop = false;
                System.out.println("Server closed.");
                connection.close();

            } else{
                if(message.equals("ping"))
                    connection.sendMessage("pong");
                else
                    System.out.println(message);
            }
        }
    }
}
