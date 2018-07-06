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
     *
     * @param o
     */
    public synchronized void deleteObserver(Observer o) {
        obs.remove(o);
    }

    public void notifyObservers() {

        Object[] arrLocal;

        synchronized (this) {
            arrLocal = obs.toArray();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this);
    }

    public void notifyObservers(String message) {
        Object[] arrLocal;

        synchronized (this) {
            arrLocal = obs.toArray();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(message);
    }

    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    public synchronized int countObservers() {
        return obs.size();
    }

    public abstract void display(ViewVisitor visitor);

    public abstract String convert(SocketVisitor visitor);
}
