package it.polimi.ingsw.client;

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
            } else {
                switch(message){
                    case "ping":
                        connection.sendMessage("pong");
                        break;
                    case "login<insert_credentials>":
                        login();
                        break;
                    case "lobby<last_access><insert_last_access>":
                        lobby();
                        break;
                    default:
                        System.out.println(message);
                }
            }
        }
    }

    private void lobby(){
        System.out.println("lobby<last_access><insert_last_access>");
    }

    private void login(){
        System.out.println("login<insert_credentials>");
    }

    private void game(){
        //GAME
    }
}
