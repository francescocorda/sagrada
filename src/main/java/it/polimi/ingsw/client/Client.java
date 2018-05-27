package it.polimi.ingsw.client;

import it.polimi.ingsw.ClientHandlerInterface;
import it.polimi.ingsw.client.RMI.RMIClientImplementation;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.exceptions.NotValidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    private static ConnectionSocket connection;
    private static Socket socket;
    private static BufferedReader is;
    private static  MessagePrinter mp;
    private static MessageDealer md;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("What communication technology do you want to use? (type \"RMI\" for RMI  and \"socket\" for socket ...");
        String technology = in.nextLine();
        if (technology.equals("RMI"))
            rmi();
        else
            socket();
    }

    public Client getClient(){
        return this;
    }


    private static void rmi() {
        String username = new String();
        String password = new String();
        boolean logged = false;
        ClientHandlerInterface server = null;
        RMIClientInterface client = null;
        try {
            client = (RMIClientInterface) UnicastRemoteObject.exportObject(new RMIClientImplementation(), 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Insert server IP address: (type 0 for default value: localhost)");
        String address = in.nextLine();
        if (0 == Integer.parseInt(address)) address = "localhost";
        try {
            server = (ClientHandlerInterface) Naming.lookup("//" + address + "/SagradaRMIServer");
            while (!logged) {
                System.out.println("Insert username: ");
                username = in.nextLine();
                System.out.println("Insert password: ");
                password = in.nextLine();
                try {
                    server.login(username, password, client);
                    System.out.println("Login success.");
                    logged = true;
                } catch (NotValidInputException e) {
                    System.out.println("Login failed.");
                }
            }
        } catch (MalformedURLException e) {
            System.err.println("URL not found!");
        } catch (RemoteException e) {
            System.err.println("Connection error: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.err.println("Passed reference is null!");
        }
        System.out.println("Insert last time you visited a cathedral: ");
        boolean lobby = false;
        while (!lobby) {
            try {
                long time = in.nextLong();
                server.joinLobby(username, time);
                lobby = true;
            } catch (RemoteException e) {
                System.out.println("Player disconneted.");
            } catch (NotValidInputException e) {
                System.out.println("Invalid time. Please insert a correct time: ");
            } catch (InputMismatchException e) {
                System.out.println("Insert last time you visited a cathedral: ");
                in.nextLine();
            }
        }
        in.close();
    }


    private static void socket() {
        md = new MessageDealer();
        boolean online = true;
        initialize();
        Scanner in = new Scanner(System.in);
        while(online){
            String message = in.nextLine();
            if(message.equals("quit")){
                connection.sendMessage(message);
                Thread.currentThread().interrupt();
            } else {
                md.setMessage(message);
            }
        }
    }

    private static void initialize() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Insert server IP address: (Type 0 for default: \"localhost\") ");
            String address = in.nextLine();
            if (address.equals("0"))
                address = "localhost";
            System.out.println("Insert server port: (Type 0 for default: \"3001\") ");
            int port = in.nextInt();
            if (port == 0)
                port = 3001;
            socket = new Socket(address, port);
            System.out.println("Connected.");
            connection = new ConnectionSocket(socket);
            mp = new MessagePrinter(connection, md);
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            System.out.println("Connection Error.");
            e.printStackTrace();
        }
    }
}
