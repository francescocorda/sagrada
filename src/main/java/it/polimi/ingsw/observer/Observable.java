package it.polimi.ingsw.observer;

import it.polimi.ingsw.SocketVisitor;
import it.polimi.ingsw.view.ViewVisitor;

import java.io.Serializable;
import java.util.Vector;

public abstract class Observable implements Serializable {
    private transient Vector<Observer> obs;

    public Observable() {
        this.obs = new Vector<>();
    }

    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

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
