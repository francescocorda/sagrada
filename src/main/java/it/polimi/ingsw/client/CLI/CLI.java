package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.Communicator;
import it.polimi.ingsw.client.CommunicatorRMI;
import it.polimi.ingsw.client.CommunicatorSocket;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.CLIView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CLI {

    private Scanner in;
    private Communicator communicator;
    private String username;
    private static CLIView view;
    public static final String DEFAULT_SERVER = "localhost";
    public static final String DEFAULT_SERVER_PORT = "3001";
    private static final String INPUT_STREAM_SEPARATOR_SYMBOL = " ";
    private static final String MESSAGE_SEPARATOR_SYMBOL = "/";

    public CLI() {
        this.view = new CLIView();
        this.in = new Scanner(System.in);
        this.username = new String();
    }

    public void startCLI() {
        toScreen("What communication technology do you want to use? " +
                "(type \"RMI\" for RMI  and \"socket\" for socket ...)");
        boolean temp = true;
        while (temp) {
            String mode = in.nextLine();
            mode = mode.toLowerCase();
            switch(mode){
                case "rmi":
                    temp = false;
                    startRMI();
                    break;
                case "socket":
                    temp = false;
                    startSocket();
                    break;
                default:
                    toScreen("Wrong input...\n");
                    toScreen("What communication technology do you want to use? " +
                            "(type \"RMI\" for RMI  and \"socket\" for socket ...)");
            }
        }
        login();
    }

    private void startRMI(){
        communicator = new CommunicatorRMI(view);
        boolean temp = true;
        String server = new String();
        while (temp) {
            toScreen("Insert server IP (leave it blank for default: localhost");
            server = in.nextLine();
            if(server.equals("")){
                server = DEFAULT_SERVER;
            }
            ArrayList<String> parameters = new ArrayList<>();
            parameters.add(server);
            try {
                communicator.initialize(parameters);
                temp = false;
            } catch (NetworkErrorException e) {
                if(server.equals(DEFAULT_SERVER))
                    toScreen("Server Offline");
                else
                    toScreen("Server Offline or WRONG ip address");
            }
        }
    }

    private void startSocket(){
        communicator = new CommunicatorSocket(view);
        boolean temp = true;
        String server = new String();
        String port = new String();
        while (temp) {
            ArrayList<String> parameters = new ArrayList<>();
            toScreen("Insert server IP (leave it blank for default: localhost)");
            server = in.nextLine();
            if(server.equals("")){
                server = DEFAULT_SERVER;
            }
            parameters.add(server);
            toScreen("Insert server port (leave it blank for default: 3001)");
            port = in.nextLine();
            if(port.equals("")){
                port = DEFAULT_SERVER_PORT;
            }
            parameters.add(port);
            try {
                communicator.initialize(parameters);
                temp = false;
            } catch (NetworkErrorException e) {
                if(server.equals(DEFAULT_SERVER))
                    toScreen("Server Offline");
                else
                    toScreen("Server Offline or WRONG ip address / port");
            }
        }
    }

    private void login(){
        boolean temp = true;
        String password;
        while (temp) {
            toScreen("LOGIN");
            toScreen("Username: ");
            username = in.nextLine();
            toScreen("Password: ");
            password = in.nextLine();
            try {
                communicator.login(username, password);
                temp = false;
            } catch (NetworkErrorException e) {
                toScreen("Server Offline / Network Error");
                startCLI();
            } catch (NotValidInputException e) {
                toScreen("Wrong password or user already taken");
            }
        }
        view.setUsername(username);
        lobby();
    }

    private void lobby(){
        boolean temp = true;
        String timeString;
        long time;
        while (temp) {
            toScreen("LOBBY");
            toScreen("When was the last time you visited a Cathedral? ");
            timeString = in.nextLine();
            try {
                time = Long.parseLong(timeString);
                communicator.lobby(username, time);
                temp = false;
            } catch (NetworkErrorException e) {
                toScreen("Server Offline / Network Error");
                startCLI();
            } catch (NotValidInputException | NumberFormatException e) {
                toScreen("Invalid time");
            }
        }
        game();
    }

    private void game(){
        boolean temp = true;
        String message = new String();
        while(temp){
            message = in.nextLine();
            ArrayList<String> commands = new ArrayList<>(Arrays.asList(message.split("\\s*"+INPUT_STREAM_SEPARATOR_SYMBOL+"\\s*")));
            message = String.join(MESSAGE_SEPARATOR_SYMBOL, commands);
            try {
                if(message.equals("quit"))
                    temp = false;
                communicator.sendMessage(message);
            } catch (NetworkErrorException e) {
                toScreen("Server Offline / Network Error");
                temp = false;
            }
        }
        startCLI();
    }

    private static void toScreen(String message) {
        System.out.println(message);
    }
}
