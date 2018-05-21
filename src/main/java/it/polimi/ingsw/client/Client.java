package it.polimi.ingsw.client;

import it.polimi.ingsw.MessageReader;
import it.polimi.ingsw.client.RMI.ConnectionRMIClient;
import it.polimi.ingsw.client.RMI.RMIClientImplementation;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.Server.RMIServerInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client {
    private static Connection connection;
    private static MessageReader messageReader;
    private static Socket socket;
    private static BufferedReader is;


    public static void main(String[] args) {
        //rmi();
        socket();
    }


    private static void rmi(){
        RMIServerInterface server;
        try {
            server = (RMIServerInterface) Naming.lookup("//localhost/SagradaRMIServer");
            RMIClientImplementation client = new RMIClientImplementation();
            RMIClientInterface remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
            server.addClient(remoteRef);
            connection = new ConnectionRMIClient(server, remoteRef);
            Scanner scanner = new Scanner(System.in);
            boolean active = true;
            while(active){
                System.out.println("Message: ");
                String text = scanner.nextLine();
                connection.sendMessage(text);
            }
            scanner.close();

        } catch (MalformedURLException e) {
            System.err.println("URL not found!");
        } catch (RemoteException e) {
            System.err.println("Connection error: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.err.println("Passed reference is null!");
        }
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
            socket = new Socket("localhost", 3001);
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
