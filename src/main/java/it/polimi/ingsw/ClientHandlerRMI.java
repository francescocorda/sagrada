package it.polimi.ingsw;

import it.polimi.ingsw.Server.ServerMain;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.exceptions.NotValidInputException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import static it.polimi.ingsw.connection.ConnectionMode.RMI;

public class ClientHandlerRMI extends UnicastRemoteObject implements ClientHandlerInterface {
    private PlayerDatabase playerDatabase;

    public ClientHandlerRMI(PlayerDatabase playerDatabase) throws RemoteException {
        super(0);
        this.playerDatabase = playerDatabase;
    }

    public void login(String username, String password, RMIClientInterface client)throws NotValidInputException, RemoteException {

        System.out.println("Client number "+ ServerMain.getServerMain().getNewClientNumber()+" connected through RMI");
        if (playerDatabase.check(username, password, RMI)) {
            System.out.println("User: "+username+" logged in.");
            PlayerDatabase.getPlayerDatabase().addRMIClient(username, client);
            Timer timer = new Timer(username, client);
            timer.start();
        } else {
            throw new NotValidInputException();
        }
    }

    public void joinLobby(String username, long time) throws NotValidInputException, RemoteException {
        long systemTime = System.currentTimeMillis()/1000; //current unix time in seconds
        if (systemTime > time) {
            Lobby.getLobby().addPlayer(username, time);
        } else throw new NotValidInputException();
    }

    public class Timer extends Thread{
        String username;
        RMIClientInterface client;
        Timer(String username, RMIClientInterface client){
            this.username=username;
            this.client=client;
        }
        public void run() {
            boolean flag=true;
            while (flag) {
                try {
                    client.checkConnection();
                } catch (RemoteException e) {
                    playerDatabase.removeRMIClient(username);
                    Timer.interrupted();
                    flag=false;
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
