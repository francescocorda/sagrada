package it.polimi.ingsw;

import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.Arrays;

public class SocketReader extends Thread {

    public SocketReader(ConnectionSocket connection, String username) {
        String message;
        boolean flag = true;
            message = "" + username + "/";
            try {
                message = message.concat(connection.getMessage());
            } catch (Exception e) {
                this.interrupt();
            }
            System.out.println("received message: " + message);
            ArrayList<String> commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
            VirtualView virtualView = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(commands.get(0));
            virtualView.notifyObservers(message);
        this.interrupt();
    }
}
