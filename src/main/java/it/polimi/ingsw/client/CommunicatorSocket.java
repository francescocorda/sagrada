package it.polimi.ingsw.client;

import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.View;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class CommunicatorSocket implements Communicator {

    private View view;
    private ConnectionSocket connection;
    private MessageGetter mg;

    /**
     * creates a {@link CommunicatorSocket} Object from a given {@link #view}.
     * @param view is the given view on witch the {@link CommunicatorSocket} operates
     */
    public CommunicatorSocket(View view) {
        this.view = view;
    }

    /**
     * initialise socket communication's field to allow communication with the server.
     * It does that by getting all the needed field from a given {@link ArrayList<String>}:
     * the first parameter should be the server's address (IP) while the second should be
     * the port to connect with it. It also initialise a {@link MessageGetter}
     * @param IPaddress is the address of the server
     * @param port is the port of the server
     * @throws NetworkErrorException if  a  {@link IOException} is thrown
     */
    @Override
    public void initialize(String IPaddress, int port) throws NetworkErrorException {
        try {
            Socket socket = new Socket(IPaddress, port);
            connection = new ConnectionSocket(socket);
        } catch (IOException e) {
            throw new NetworkErrorException();
        }
        this.mg = new MessageGetter(connection, view);
    }

    /**
     * sends a login message to the server.
     * it does that by sending a message through the use of method {@link ConnectionSocket#sendMessage(String)},
     * the message format is "login/@USERNAME/@PASSWORD" where @USERNAME is the field for user's username
     * and @PASSWORD is the one for user's password
     * @param username is the parameter for user's username
     * @param password is the parameter for user's password
     * @throws NetworkErrorException if  a  {@link IOException} is thrown
     * @throws NotValidInputException if the given parameters aren't correct
     */
    @Override
    public void login(String username, String password) throws NetworkErrorException, NotValidInputException {
        String returnedMessage;
        getMessage(); //Clean buffer
        try {
            //waiting message protocol to be updated
            connection.sendMessage("login/" + username + "/" + password);
            returnedMessage = getMessage();
        } catch (Exception e) {
            throw new NetworkErrorException();
        }
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(returnedMessage.split("\\s*/\\s*")));
        if (commands.remove(0).equals("login")) {
            switch (commands.remove(0)) {
                case "success":
                    break;
                case "failed":
                case "invalid_command":
                    throw new NotValidInputException();
                default:
                    System.err.println(returnedMessage);
                    throw new NetworkErrorException();
            }
        } else {
            System.out.println("ERROR: message received: " + returnedMessage);
        }
    }

    /**
     * sends a message towards the server to join the {@link it.polimi.ingsw.Lobby}.
     * it does that through by sending a message through the use of method {@link ConnectionSocket#sendMessage(String)},
     * the message format is "lobby/@USERNAME/@TIME" where @USERNAME is the field for user's username
     * and @PASSWORD is the one for user's password
     * @param username is the parameter for user's username
     * @param time is the parameter for user's last access to a cathedral
     * @throws NetworkErrorException if  a  {@link IOException} is thrown
     * @throws NotValidInputException if the given time is not valid
     */
    @Override
    public void lobby(String username, long time) throws NetworkErrorException, NotValidInputException {
        String returnedMessage;
        try {
            connection.sendMessage("lobby/last_access/" + time);
            returnedMessage = getMessage();
        } catch (Exception e) {
            throw new NetworkErrorException();
        }
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(returnedMessage.split("\\s*/\\s*")));
        String phase = commands.remove(0);
        if (phase.equals("lobby")) {
            switch (commands.remove(0)) {
                case "last_access":
                    if(!commands.isEmpty() && commands.remove(0).equals("invalid_time"))
                        throw new NotValidInputException();
                    break;
                case "invalid_command":
                    throw new NotValidInputException();
                default:
                    break;
            }
        } else if (phase.equals("game") && commands.size() == 2 && commands.remove(0).equals("message")
                && commands.remove(0).equals("back_to_game")) {
            mg.unlock();
            view.displayMessage("Welcome back!");
        }
    }

    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try {
            connection.sendMessage(message);
        } catch (Exception e) {
            throw new NetworkErrorException();
        }
    }

    public String getMessage() {
        while (!mg.readable()) {
            System.out.print(""); //do nothing
        }
        return mg.getMessage();
    }
}
