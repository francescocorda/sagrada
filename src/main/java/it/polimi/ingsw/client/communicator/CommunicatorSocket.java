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
    private static final String SEPARATOR_SYMBOL = "/";
    private static final String LOGIN = "login";
    private static final String LOBBY = "lobby";
    private static final String LAST_ACCESS = "last_access";
    private static final String SUCCESS = "success";
    private static final String FAILED = "failed";
    private static final String LOGOUT = "logout";
    private static final String INVALID_COMMAND = "invalid_command";
    private static final String INVALID_TIME = "invalid_time";
    private static final String ERROR_MESSAGE = "ERROR: message received: ";

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
        getMessage(); //clean buffer
        try {
            connection.sendMessage(LOGIN+SEPARATOR_SYMBOL + username + SEPARATOR_SYMBOL + password);
            returnedMessage = getMessage();
        } catch (Exception e) {
            throw new NetworkErrorException();
        }
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(returnedMessage.split("\\s*/\\s*")));
        if (commands.remove(0).equals(LOGIN)) {
            switch (commands.remove(0)) {
                case SUCCESS:
                    break;
                case FAILED:
                case INVALID_COMMAND:
                    throw new NotValidInputException();
                default:
                    println(returnedMessage);
                    throw new NetworkErrorException();
            }
        } else {
            println(ERROR_MESSAGE + returnedMessage);
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
            connection.sendMessage(LOBBY+SEPARATOR_SYMBOL+LAST_ACCESS+SEPARATOR_SYMBOL + time);
            returnedMessage = getMessage();
        } catch (Exception e) {
            throw new NetworkErrorException();
        }
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(returnedMessage.split("\\s*/\\s*")));
        String phase = commands.remove(0);
        if (phase.equals(LOBBY)) {
            switch (commands.remove(0)) {
                case LAST_ACCESS:
                    if(!commands.isEmpty() && commands.remove(0).equals(INVALID_TIME))
                        throw new NotValidInputException();
                    break;
                case INVALID_COMMAND:
                    throw new NotValidInputException();
                default:
                    break;
            }
        }
    }

    /**
     * sends a given message to the server.
     * @param message is the given message to be sent to the server
     * @throws NetworkErrorException if any error occurs
     */
    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try {
            if(message.equals(LOGOUT))
                mg.setLogout(true);
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
     * @param message : the given {@link String} message
     */
    private void println(String message){
        System.out.println(message);
    }
}
