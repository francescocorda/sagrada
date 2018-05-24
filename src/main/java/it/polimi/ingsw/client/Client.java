package it.polimi.ingsw.client;

import it.polimi.ingsw.ClientHandlerInterface;
import it.polimi.ingsw.connection.Connection;
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
import java.util.Scanner;

public class Client {
    private static ConnectionSocket connection;
    private static Socket socket;
    private static BufferedReader is;


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("What communication technology do you wont to use? (type \"RMI\" for RMI  and \"socket\" for socket ...");
        String technology = in.nextLine();
        if(technology.equals("RMI"))
            rmi();
        else
            socket();
    }


    private static void rmi(){
        String username = new String();
        String password = new String();
        boolean logged = false;
        ClientHandlerInterface server=null;
        Scanner in = new Scanner(System.in);
        System.out.println("Insert server IP address: (type 0 for default value: localhost)");
        String address = in.nextLine();
        if(0==Integer.parseInt(address)) address = "localhost";
        try {
            server = (ClientHandlerInterface) Naming.lookup("//" + address + "/SagradaRMIServer");
            while(!logged) {
                System.out.println("Insert username: ");
                username = in.nextLine();
                System.out.println("Insert password: ");
                password = in.nextLine();
                try {
                    server.login(username, password);
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
        while(!lobby){
            long time = in.nextLong();
            try {
                server.joinLobby(username, time);
                lobby=true;
                System.out.println("Joined to the lobby.");
            } catch (RemoteException e) {
                System.out.println("Player disconneted.");
            } catch (NotValidInputException e) {
                System.out.println("Invalid time. Please insert a correct time: ");
            }
        }
        in.close();
    }


    private static void socket() {
        boolean online = true;
        Scanner scanner = new Scanner(System.in);

        initialize();
        while (online) {
            String text = scanner.nextLine();
            connection.sendMessage(text);
        }
        scanner.close();
    }


        private static void initialize() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Insert server IP address: ");
            String address = in.nextLine();
            System.out.println("Insert server port: ");
            int port = in.nextInt();
            socket = new Socket(address, port);
            System.out.println("Connected.");
            connection = new ConnectionSocket(socket);
            MessagePrinter mp = new MessagePrinter(connection);
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            System.out.println("Connection Error.");
            e.printStackTrace();
        }
    }


}
