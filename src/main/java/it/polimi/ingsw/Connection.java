package it.polimi.ingsw;

public interface Connection {
    void sendMessage(String message);
    String getMessage();
    void close();
    boolean isConnected();
}
