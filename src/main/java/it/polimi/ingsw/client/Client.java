package it.polimi.ingsw.client;

import it.polimi.ingsw.client.game_mode.cli.CLI;
import it.polimi.ingsw.client.game_mode.gui.login.Login;
import java.util.Scanner;

public class Client {

    /**
     * starts client and allows to choose whether to play through cli or gui
     * @param args the usual main args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        println("Would you like to play in gui or cli?");
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
                    println("Wrong input...\n");
                    println("Would you like to play in gui or cli?");
            }
        }
    }

    /**
     * shows to commandLine the given message
     * @param message the given message
     */
    private static void println(String message) {
        System.out.println(message);
    }
}
