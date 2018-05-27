package it.polimi.ingsw.client;

import it.polimi.ingsw.connection.ConnectionSocket;

public class MessagePrinter extends Thread {
    private ConnectionSocket connection;
    private MessageDealer md;

    public MessagePrinter(ConnectionSocket connection, MessageDealer md) {
        this.connection = connection;
        this.md = md;
        start();
    }

    @Override
    public void run() {
        boolean loop = true;
        while (loop) {

            String message = connection.getMessage();
            if (message == null) {
                loop = false;
                toScreen("Server closed.");
                connection.close();
            } else {
                switch (message) {
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
                        toScreen(message);
                }
            }
        }
    }

    private void lobby() {
        long time = 0;
        boolean isValid = true;
        toScreen("Insert last time you visited a cathedral: ");
        try {
            time = Long.parseLong(readFromInputStream());
        } catch (NumberFormatException e) {
            isValid = false;
        }
        if (isValid) {
            connection.sendMessage("lobby<last_access><" + time + ">");
        }
    }

    private void login() {
        String username;
        String password;
        toScreen("Insert username: ");
        username = readFromInputStream();
        toScreen("Insert password: ");
        password = readFromInputStream();
        connection.sendMessage("login<" + username + "><" + password + ">");
    }

    private void game() {
        //GAME
    }

    private void toScreen(String message) {
        System.out.println(message);
    }

    private String readFromInputStream(){
        while(md.checkWait()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                toScreen("Error: "+e);
            }
        }
        return  md.getMessage();
    }
}
