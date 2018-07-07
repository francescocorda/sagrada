package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.socket.SocketVisitor;
import it.polimi.ingsw.view.ViewVisitor;

import java.io.Serializable;
import java.util.Vector;

public abstract class Observable implements Serializable {
    private transient Vector<Observer> obs;

    /**
     * return a new {@link Observable}
     */
    public Observable() {
        this.obs = new Vector<>();
    }

    /**
     * adds a given {@link Observer} o to {@link #obs}
     * @param o : the given {@link Observer}
     */
    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    /**
     * deletes the given {@link Observer} o from this {@link Observable}.
     * @param o : the given {@link Observer} o
     */
    public synchronized void deleteObserver(Observer o) {
        obs.remove(o);
    }

    /**
     * notifies all {@link Observer} of this {@link Observable} about it's current state.
     */
    public void notifyObservers() {

        Object[] arrLocal;

        synchronized (this) {
            arrLocal = obs.toArray();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this.copy());
    }

    /**
     * notifies all {@link Observer} of this {@link Observable} about a given {@link String} message.
     * @param message : the given {@link String} message
     */
    public void notifyObservers(String message) {
        Object[] arrLocal;

        synchronized (this) {
            arrLocal = obs.toArray();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(message);
    }

    /**
     * deletes all {@link Observer}s of this {@link Observable}
     */
    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    /**
     * @return the number of this {@link Observable} {@link Observer}s.
     */
    public synchronized int countObservers() {
        return obs.size();
    }

    /**
     * @return a deep copy of the {@link Observable}.
     */
    public abstract Observable copy();

    /**
     * shows it's state through a given {@link ViewVisitor} visitor.
     * @param visitor : the given {@link ViewVisitor} visitor
     */
    public abstract void display(ViewVisitor visitor);

    /**
     *
     * @param visitor
     * @return
     */
    public abstract String convert(SocketVisitor visitor);
}
