package it.polimi.ingsw.client;

import it.polimi.ingsw.client.CLI.CLI;
import it.polimi.ingsw.client.GUI.login.Login;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        toScreen("Would you like to play in GUI or CLI?");
        boolean temp = true;
        while (temp) {
            String mode = in.nextLine();
            mode = mode.toLowerCase();
            switch(mode){
                case "gui":
                    temp = false;
                    Login login = new Login();
                    login.showGUI();
                    break;
                case "cli":
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
