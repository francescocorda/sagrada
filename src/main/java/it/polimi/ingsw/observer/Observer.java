package it.polimi.ingsw.observer;

public interface Observer {
    void update(String message);
    void update(Observable o);
}
