package it.polimi.ingsw.Server;

import it.polimi.ingsw.Lobby;
import java.util.Scanner;

public class ServerMain {

    private static boolean SERVER_UP;
    private static SocketServer serverSocket;
    private static RMIServer rmiServer;
    private static int SOCKET_PORT;
    private static int RMI_PORT;
    private static ServerMain instance = null;
    private int numberOfClient  = -1;
    private static int timerSeconds = 2*60;

    private ServerMain(){
    }

    public static synchronized ServerMain getServerMain() {
        if (instance == null) {
            instance = new ServerMain();
        }
        return instance;
    }

    public static void main( String[] args ) {

        //Initialize all the structure for the game/server
        Scanner scanner = new Scanner(System.in);
        SERVER_UP=true;
        System.out.println("Set socket port (type 0 for default value: 3001): ");
        String text = scanner.nextLine();
        int temp;
        if((temp=Integer.valueOf(text)) == 0)
            SOCKET_PORT =3001;
        else
            SOCKET_PORT=temp;
        System.out.println("Set RMI Server port (type 0 for default value: 1099): ");
        text = scanner.nextLine();
        if((temp=Integer.valueOf(text)) == 0)
            RMI_PORT = 1099;
        else
            RMI_PORT = temp;
        System.out.println("Set startGame timer[seconds] (0 for default 2*60): ");
        text = scanner.nextLine();
        int seconds = Integer.valueOf(text);
        if(seconds>0)
            setTimerSeconds(seconds);

        Lobby.getLobby();
        start();
    }

    private static void start(){
        rmiServer = new RMIServer(RMI_PORT);
        serverSocket = new SocketServer(SOCKET_PORT);

    }

    public static boolean getStatus(){
        return SERVER_UP;
    }

    public int getNewClientNumber(){
        numberOfClient++;
        return numberOfClient;
    }

    private static void setTimerSeconds(int seconds){
        timerSeconds = seconds;
    }

    public int getTimerSeconds(){
        return timerSeconds;
    }
}
