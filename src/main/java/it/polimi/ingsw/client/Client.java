package it.polimi.ingsw.client;

import it.polimi.ingsw.ClientHandlerInterface;
import it.polimi.ingsw.client.RMI.RMIClientImplementation;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.CLIView;
import it.polimi.ingsw.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    private static ConnectionSocket connection;
    private static Socket socket;
    private static BufferedReader is;
    private static MessagePrinter mp;
    private static MessageDealer md;
    private static View view;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        toScreen("What communication technology do you want to use? (type \"RMI\" for RMI  and \"socket\" for socket ...)");
        String technology = in.nextLine();
        view = new CLIView();
        if (technology.equals("RMI"))
            rmi();
        else
            socket();
    }

    public Client getClient() {
        return this;
    }


    private static void rmi() {
        String username = new String();
        String password = new String();
        boolean logged = false;
        ClientHandlerInterface server = null;
        RMIClientInterface client = null;
        try {
            client = (RMIClientInterface) UnicastRemoteObject.exportObject(new RMIClientImplementation(view), 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Scanner in = new Scanner(System.in);
        toScreen("Insert server IP address: (type 0 for default value: localhost)");
        String address = in.nextLine();
        if (address.length() < 2) address = "localhost";
        try {
            server = (ClientHandlerInterface) Naming.lookup("//" + address + "/ClientHandler");

            while (!logged) {
                toScreen("Insert username: ");
                username = in.nextLine();
                toScreen("Insert password: ");
                password = in.nextLine();
                try {
                    server.login(username, password, client);
                    toScreen("Login success.");
                    logged = true;
                } catch (NotValidInputException e) {
                    toScreen("Login failed.");
                }
            }
        } catch (MalformedURLException e) {
            toScreen("URL not found!");
        } catch (RemoteException e) {
            toScreen("Connection error: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            toScreen("Passed reference is null!");
        }
        ((CLIView) view).setUsername(username);
        toScreen("Insert last time you visited a cathedral: ");
        boolean lobby = false;
        String text;
        long time;
        while (!lobby && server != null) {
            try {
                text = in.nextLine();
                time = Long.parseLong(text);
                server.joinLobby(username, time);
                lobby = true;
            } catch (RemoteException e) {
                toScreen("Player disconneted.");
            } catch (NotValidInputException | NumberFormatException e) {
                toScreen("Invalid time. Please insert a correct time: ");
            } catch (InputMismatchException e) {
                toScreen("Insert last time you visited a cathedral: ");
            }
        }
        boolean endGame = false;
        ArrayList<String> commands = new ArrayList<>();
        commands.add(username);
        while (!endGame) {
            String command = in.nextLine();
            if (!command.equals("")) {
                commands.add(command);
            } else {
                String message = String.join("/ ", commands);
                commands.clear();
                try {
                    if (server != null)
                        server.update(message);
                    else
                        throw new NullPointerException();
                } catch (RemoteException | NullPointerException e) {
                    toScreen("Server Closed");
                    endGame = true;
                }
                commands = new ArrayList<>();
                commands.add(username);
            }
        }
        in.close();
    }

    private static void socket() {
        md = new MessageDealer();
        initialize();
        Scanner in = new Scanner(System.in);
        //new method
        boolean endGame = false;
        ArrayList<String> commands = new ArrayList<>();
        while (!endGame) {
            String command = in.nextLine();
            switch(command){
                case "quit":
                    connection.sendMessage(command);
                    endGame = true;
                    break;
                case "":
                    String message = String.join("/", commands);
                    connection.sendMessage(message);
                    commands.clear();
                    commands = new ArrayList<>();
                    break;
                case "move":
                case "skip":
                case "toolcard":
                    commands.add(command);
                    break;
                default:
                    if (command.length() > 1) {
                        md.setMessage(command);
                    } else {
                        commands.add(command);
                    }
            }
        }
        in.close();
        Thread.currentThread().interrupt();
    }

    private static void initialize() {
        try {
            Scanner in = new Scanner(System.in);
            toScreen("Insert server IP address: (Type 0 for default: \"localhost\") ");
            String address = in.nextLine();
            if (address.equals("0"))
                address = "localhost";
            toScreen("Insert server port: (Type 0 for default: \"3001\") ");
            int port = in.nextInt();
            if (port == 0)
                port = 3001;
            socket = new Socket(address, port);
            toScreen("Connected.");
            connection = new ConnectionSocket(socket);
            mp = new MessagePrinter(connection, md, view);
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            toScreen("Connection Error.");
            if (mp != null)
                mp.interrupt();
            Thread.currentThread().interrupt();
        }
    }

    private static void toScreen(String message) {
        System.out.println(message);
    }
}
