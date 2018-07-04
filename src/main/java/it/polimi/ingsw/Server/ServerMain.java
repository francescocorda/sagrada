package it.polimi.ingsw.Server;

import it.polimi.ingsw.Lobby;
import it.polimi.ingsw.exceptions.NetworkErrorException;

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

    private ServerMain() {
    }

    public static synchronized ServerMain getServerMain() {
        if (instance == null) {
            instance = new ServerMain();
        }
        return instance;
    }

    /**
     * Initialize all the structure for the game/server
     * @param args the usual main args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text;
        serverUp = true;
        boolean flag = true;
        setSocketServer();
        setRMIServer();
        setTimerLobby();
        setTimerPlayerTurn();
        Lobby.getLobby();
        start();
    }

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

    private static void start() {
        Scanner scanner = new Scanner(System.in);
        String text;
        boolean flag = true;
        println("SERVER READY...");
        while(flag){
            text = scanner.nextLine();
            text = text.toLowerCase();
            if(text.equals("quit")){
                flag = false;
                serverUp = false;
                serverSocket.close();
                Lobby.getLobby().close();
                println("SERVER CLOSED...");
                System.exit(0);
            }
        }
    }

    public static boolean getStatus() {
        return serverUp;
    }

    public int getNewClientNumber() {
        numberOfClient++;
        return numberOfClient;
    }

    private static void setTimerPlayerTurn(int seconds){
        turnSeconds = seconds;
    }

    public int getTurnSeconds(){
        return turnSeconds;
    }

    private static void setTimerSeconds(int seconds) {
        timerSeconds = seconds;
    }

    public int getTimerSeconds() {
        return timerSeconds;
    }

    private static void println(String message) {
        System.out.println(message);
    }
}
