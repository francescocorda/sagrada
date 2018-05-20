package it.polimi.ingsw.connection;

public interface Connection {
    void sendMessage(String message);
    String getMessage();
    void close();
}
