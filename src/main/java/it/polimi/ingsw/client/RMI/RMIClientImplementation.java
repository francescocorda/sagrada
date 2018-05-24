package it.polimi.ingsw.client.RMI;

public class RMIClientImplementation implements RMIClientInterface {

    public void send(String message) {
        System.out.println(message);
    }

}
