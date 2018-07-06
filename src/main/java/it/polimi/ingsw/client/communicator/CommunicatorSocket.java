package it.polimi.ingsw.client.communicator;

import it.polimi.ingsw.client.connection_mode.socket.MessageGetter;
import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.server.Lobby;
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
     * @param view is the given view on which the {@link CommunicatorSocket} operates
     */
    public CommunicatorSocket(View view) {
        this.view = view;
    }

    /**
     * initialise socket communication's field to allow communication with the server.
     * It does that by getting all the needed field from a given {@link ArrayList<String>}:
     * the first parameter should be the server's address (IP) while the second should be
     * the port to connect with it. It also initialise a {@link MessageGetter}
     * @param addressIP is the address of the server
     * @param port is the port of the server
     * @throws NetworkErrorException if  a  {@link IOException} is thrown
     */
    @Override
    public void initialize(String addressIP, int port) throws NetworkErrorException {
        try {
            Socket socket = new Socket(addressIP, port);
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
                    println(returnedMessage);
                    throw new NetworkErrorException();
            }
        } else {
            println("ERROR: message received: " + returnedMessage);
        }
    }

    /**
     * sends a message towards the server to join the {@link Lobby}.
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
        }
    }

    /**
     * sends a given message to the server.
     * it does that through method {@link ConnectionSocket {@link #sendMessage(String)}}
     * @param message is the given message to be sent to the server
     * @throws NetworkErrorException if any error occurs
     */
    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try {
            connection.sendMessage(message);
        } catch (Exception e) {
            throw new NetworkErrorException();
        }
    }

    /**
     * get a message from {@link MessageGetter}.
     * it does that by checking if there's something to be red through the use of method
     * {@link MessageGetter#readable()}, if it is readable then it get the message through method
     * {@link MessageGetter#getMessage()}, if not it keeps checking until it is
     * @return {@link String} message from {@link MessageGetter}
     */
    public synchronized String getMessage() {
        while (!mg.readable()) {
            //do nothing
        }
        return mg.getMessage();
    }

    /**
     * closes {@link CommunicatorSocket}.
     * it does that by calling {@link MessageGetter#kill()} and {@link ConnectionSocket#close()}
     */
    public void close(){
        mg.kill();
        connection.close();
    }

    /**
     * show on screen a given message.
     * @param message
     */
    private void println(String message){
        //TODO substitute with a LOGGER
        System.out.println(message);
    }
}
