package it.polimi.ingsw.client;

public class MessageDealer {
    private String message;
    private boolean wait = true;
    private static final Object countLock = new Object(); //this object allow a synchronised access to different methods

    public void setMessage(String message) {
        synchronized (countLock) {
            this.message = message;
            wait = false;
        }
    }

    public String getMessage() {
        synchronized (countLock) {
            String temp = message;
            message = null;
            wait = true;
            return temp;
        }
    }

    public boolean checkWait() {
        synchronized (countLock) {
            return wait;
        }
    }
}