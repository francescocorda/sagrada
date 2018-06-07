package it.polimi.ingsw;

import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.view.VirtualView;

import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.ingsw.controller.Controller.TIMER_SECONDS;

public class SocketReader extends Thread {

    private String username;
    private VirtualView view;
    private ConnectionSocket connection;
    private boolean flag;
    private Timer timer;
    private int missingPong;
    private boolean timerOn;

    public SocketReader(ConnectionSocket connection, String username) {
        this.username = username;
        this.connection = connection;
        this.view = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(username);
        this.timer = new Timer();
        this.missingPong=0;
        this.timerOn=false;
        start();
    }

    @Override
    public void run() {
        String message;
        String temp;
        flag = true;
        missingPong=0;
        while (flag) {
            message = username + "/";
            try {
                temp = connection.getMessage();
                if(missingPong>0 && temp.equals("pong")){
                    missingPong--;
                    if(missingPong<0)
                        missingPong=0;
                    cancelTimer();
                } else {
                    if(temp.equals("quit")){
                        PlayerDatabase.getPlayerDatabase().disconnect(username);
                        flag = false;
                    }
                    message = message.concat(temp);
                    view.notifyObservers(message);
                }
            } catch (Exception e) {
                flag = false;
            }
        }
        Thread.currentThread().interrupt();
    }

    private void cancelTimer() {
        try {
            timer.cancel();
            timer = new Timer();
            timerOn = false;
        } catch (Exception e) {
            //Timer already closed
        }
    }

    private void kill() {
        flag = false;
        Thread.currentThread().interrupt();
    }

    public void waitForPong() {
        connection.sendMessage("ping");
        missingPong++;
        if(!timerOn){
            timerOn=true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    PlayerDatabase.getPlayerDatabase().disconnect(username);
                }
            }, TIMER_SECONDS);
        }
    }
}
