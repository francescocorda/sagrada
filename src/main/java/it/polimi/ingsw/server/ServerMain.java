package it.polimi.ingsw.server;

import it.polimi.ingsw.database.ClientDatabase;
import it.polimi.ingsw.database.VirtualViewsDataBase;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.server.rmi.RMIServer;
import it.polimi.ingsw.server.socket.SocketServer;

import java.util.Scanner;

public class ServerMain {

    private static final String NETWORK_ERROR = "Network Error, try a different port...";
    private static final String RMI_SETTER_MESSAGE = "Set rmi port (leave it empty for default value: ";
    private static final String SOCKET_SETTER_MESSAGE = "Set socket port (leave it empty for default value: ";
    private static final String TIMER_SETTING_MESSAGE = "Set startGame timer[seconds] (leave it empty for default ";
    private static final String TURN_SETTING_MESSAGE = "Set playerTurn timer[seconds] (leave it empty for default ";
    private static final String DEFAULT_SYMBOL = "";
    private static final String NOT_VALID_INPUT = "Not valid input...";
    private static final String CLOSING_SETTER = "): ";
    private static final String START_STATE = "###############--START_STATE--################";
    private static final String END_STATE = "################--END_STATE--#################";
    private static final String STATE = "state";
    private static final String SERVER_CLOSED = "SERVER CLOSED...";
    private static final String QUIT = "quit";
    private static final String SERVER_READY = "SERVER READY...";
    private static final String BLANK = "";
    private static boolean serverUp;
    private static SocketServer serverSocket;
    private static RMIServer rmiServer;
    private static final int DEFAULT_SOCKET_PORT = 3001;
    private static final int DEFAULT_RMI_PORT = 1099;
    private static int socketPort;
    private static int rmiPort;
    private static int turnSeconds = 2 * 60;
    private static ServerMain instance = null;
    private int numberOfClient = -1;
    private static int timerSeconds = 2 * 60;

    /**
     * creates a new {@link ServerMain}
     */
    private ServerMain() {
    }

    /**
     * @return tho only instance of {@link ServerMain}.
     */
    public static synchronized ServerMain getServerMain() {
        if (instance == null) {
            instance = new ServerMain();
        }
        return instance;
    }

    /**
     * Initialize all the structure for the game/server.
     * @param args the usual main args
     */
    public static void main(String[] args) {
        serverUp = true;
        setSocketServer();
        setRMIServer();
        setTimerLobby();
        setTimerPlayerTurn();
        Lobby.getLobby();
        start();
    }

    /**
     * sets the time of a player's turn, so the one of it's timer.
     */
    private static void setTimerPlayerTurn(){
        Scanner scanner = new Scanner(System.in);
        String text;
        boolean flag = true;
        while(flag){
            println(TURN_SETTING_MESSAGE+turnSeconds+CLOSING_SETTER);
            text = scanner.nextLine();
            try{
                if(!text.equals(DEFAULT_SYMBOL)){
                    int seconds = Integer.parseInt(text);
                    setTimerPlayerTurn(seconds);
                }
                flag = false;
            } catch (NumberFormatException e){
                println(NOT_VALID_INPUT);
            }
        }
    }

    /**
     * set the time of Lobby's countdown for {@link Lobby#startGame()}.
     */
    private static void setTimerLobby(){
        Scanner scanner = new Scanner(System.in);
        String text;
        boolean flag = true;
        while(flag){
            println(TIMER_SETTING_MESSAGE+timerSeconds+CLOSING_SETTER);
            text = scanner.nextLine();
            try{
                if(!text.equals(DEFAULT_SYMBOL)){
                    int seconds = Integer.parseInt(text);
                    setTimerSeconds(seconds);
                }
                flag = false;
            } catch (NumberFormatException e){
                println(NOT_VALID_INPUT);
            }
        }
    }

    /**
     * sets the port for a {@link SocketServer}.
     */
    private static void setSocketServer(){
        Scanner scanner = new Scanner(System.in);
        String text;
        boolean flag = true;
        while (flag) {
            println(SOCKET_SETTER_MESSAGE+DEFAULT_SOCKET_PORT+CLOSING_SETTER);
            text = scanner.nextLine();
            if (text.equals(DEFAULT_SYMBOL)){
                socketPort = DEFAULT_SOCKET_PORT;
                flag = false;
            }
            else {
                try {
                    socketPort = Integer.parseInt(text);
                    flag = false;
                } catch (NumberFormatException e) {
                    println(NOT_VALID_INPUT);
                }
            }
            if(!flag){
                try {
                    serverSocket = new SocketServer(socketPort);
                } catch (NetworkErrorException e) {
                    println(NETWORK_ERROR);
                    flag = true;
                }
            }
        }
    }

    /**
     * sets the port for a {@link RMIServer}.
     */
    private static void setRMIServer(){
        Scanner scanner = new Scanner(System.in);
        String text;
        boolean flag = true;
        while (flag) {
            println(RMI_SETTER_MESSAGE+DEFAULT_RMI_PORT+CLOSING_SETTER);
            text = scanner.nextLine();
            if (text.equals(DEFAULT_SYMBOL)){
                rmiPort = DEFAULT_RMI_PORT;
                flag = false;
            }
            else {
                try {
                    rmiPort = Integer.parseInt(text);
                    flag = false;
                } catch (NumberFormatException e) {
                    println(NOT_VALID_INPUT);
                }
            }
            if(!flag){
                try{
                    rmiServer = new RMIServer(rmiPort);
                } catch (NetworkErrorException e) {
                    flag = true;
                    println(NETWORK_ERROR);
                }
            }
        }
    }

    /**
     * handle {@link ServerMain} behaviour after initialization.
     */
    private static void start() {
        Scanner scanner = new Scanner(System.in);
        String text;
        boolean flag = true;
        println(SERVER_READY);
        while(flag){
            text = scanner.nextLine();
            text = text.toLowerCase();
            if(text.equals(QUIT)){
                flag = false;
                serverUp = false;
                serverSocket.close();
                Lobby.getLobby().close();
                println(SERVER_CLOSED);
                System.exit(0);
            }
            if(text.equals(STATE)){
                showCurrentState();
            }
        }
    }

    /**
     * shows current state.
     */
    private static void showCurrentState(){
        println(START_STATE);
        Lobby.getLobby().status();
        println(BLANK);
        VirtualViewsDataBase.getVirtualViewsDataBase().status();
        println(BLANK);
        ClientDatabase.getPlayerDatabase().status();
        println(END_STATE);
    }

    /**
     * @return true if {@link ServerMain#serverUp} is true; false otherwise.
     */
    public static boolean getStatus() {
        return serverUp;
    }

    /**
     * @return the number of the new connecting client.
     */
    public int getNewClientNumber() {
        numberOfClient++;
        return numberOfClient;
    }

    /**
     * sets the seconds of a player's turn as the given seconds.
     * @param seconds : the given seconds
     */
    private static void setTimerPlayerTurn(int seconds){
        turnSeconds = seconds;
    }

    /**
     * @return of a player's turn.
     */
    public int getTurnSeconds(){
        return turnSeconds;
    }

    /**
     * sets the seconds of Lobby's timer for a {@link Lobby#startGame()} as the given seconds.
     * @param seconds : the given seconds.
     */
    private static void setTimerSeconds(int seconds) {
        timerSeconds = seconds;
    }

    /**
     * @return the seconds of Lobby's timer for a {@link Lobby#startGame()}.
     */
    public int getTimerSeconds() {
        return timerSeconds;
    }

    /**
     * displays the given {@link String} message.
     * @param message : the given {@link String} message
     */
    private static void println(String message) {
        System.out.println(message);
    }
}
