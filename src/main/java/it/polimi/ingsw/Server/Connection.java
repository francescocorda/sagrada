package it.polimi.ingsw.Server;

public interface Connection {
    void sendMessage(String message);
    String getMessage();
    void close();
    boolean isConnected();
}
