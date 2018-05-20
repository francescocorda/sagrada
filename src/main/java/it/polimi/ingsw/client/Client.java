package it.polimi.ingsw.client;

import it.polimi.ingsw.MessageReader;
import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.ConnectionSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Connection connection;
     private static MessageReader messageReader;

        private static Socket socket;
        private static BufferedReader is;


    public static void main(String[] args) {

        boolean online = true;
        Scanner scanner = new Scanner(System.in);

        initialize();
        while(online){
            String text = scanner.nextLine();
            connection.sendMessage(text);
            }
        scanner.close();
    }

    private static void initialize(){
        try {
            socket =  new Socket( "localhost", 3001);

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
