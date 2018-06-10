package it.polimi.ingsw.client;

import it.polimi.ingsw.ClientHandlerInterface;
import it.polimi.ingsw.client.CLI.CLI;
import it.polimi.ingsw.client.GUI.login.Login;
import it.polimi.ingsw.client.RMI.RMIClientImplementation;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.CLIView;
import it.polimi.ingsw.view.View;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        toScreen("Would you like to play in GUI or CLI?");
        boolean temp = true;
        while (temp) {
            String mode = in.nextLine();
            switch(mode){
                case "GUI":
                    temp = false;
                    Login login = new Login();
                    login.showGUI();
                    break;
                case "CLI":
                    temp = false;
                    new CLI().startCLI();
                    break;
                default:
                    toScreen("Wrong input...\n");
                    toScreen("Would you like to play in GUI or CLI?");
            }
        }
    }

    private static void toScreen(String message) {
        System.out.println(message);
    }
}
