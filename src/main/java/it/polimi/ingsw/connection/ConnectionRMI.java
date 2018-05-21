package it.polimi.ingsw.connection;

import it.polimi.ingsw.client.RMI.RMIClientInterface;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionRMI implements Connection{

    private RMIClientInterface client;
    private String currentMessage;
    private Timer timer;
    private boolean flag;
    private boolean messageAlreadyDelivered;

    public ConnectionRMI(RMIClientInterface client){
        this.client=client;
        currentMessage=null;
        timer = new Timer();
        messageAlreadyDelivered=false;
        flag = true;
    }

    @Override
    public void sendMessage(String message) {
        try {
            client.send(message);
        } catch (RemoteException e) {
            close();
            System.out.println("Client rimosso!");
        }
    }

    @Override
    public String getMessage() {
        if(!messageAlreadyDelivered)
            timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag=false;
            }
        }, (long) 2 * 60 * 1000); //two min timer before null return
        while(flag);
        String tempMessage=currentMessage;
        currentMessage=null;
        flag=true;
        messageAlreadyDelivered=false;
        return tempMessage;
    }

    @Override
    public void close() {
        //TODO
    }

    public void setCurrentMessage(String message){
        currentMessage=message;
        messageAlreadyDelivered=true;
        timer.cancel();
        flag=false;
    }

    public RMIClientInterface getClientInterface(){
        return client;
    }
}
