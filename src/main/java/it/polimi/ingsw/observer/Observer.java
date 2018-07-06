package it.polimi.ingsw.observer;

public interface Observer {
    /**
     * updates this {@link Observer} with the given {@link String} message.
     * @param message : the given {@link String} message
     */
    void update(String message);

    /**
     * updates this {@link Observer} with the given {@link Observable} o.
     * @param o : the given {@link Observable}
     */
    void update(Observable o);
}
