package it.polimi.ingsw.client;

import it.polimi.ingsw.MessageReader;
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

    public CommunicatorSocket(View view) {
        this.view = view;
    }

    @Override
    public void initialize(ArrayList<String> parameters) throws NetworkErrorException {
        try {
            String address = parameters.get(0);
            int port = Integer.parseInt(parameters.get(1));
            Socket socket = new Socket(address, port);
            connection = new ConnectionSocket(socket);
        } catch (IOException e) {
            throw  new NetworkErrorException();
        }
        this.mg = new MessageGetter(connection, view);
    }

    @Override
    public void login(String username, String password) throws NetworkErrorException, NotValidInputException {
        String returnedMessage;
        getMessage(); //Clean buffer
        try{
            //waiting message protocol to be updated
            connection.sendMessage("login/" + username + "/" + password);
            returnedMessage = getMessage();
        } catch (Exception e){
            throw new NetworkErrorException();
        }
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(returnedMessage.split("\\s*/\\s*")));
        if(commands.remove(0).equals("login")){
            switch(commands.remove(0)){
                case "success":
                    break;
                case "failed":
                case "invalid_command":
                    throw new NotValidInputException();
                default:
                    System.out.println(returnedMessage);
                    throw new NetworkErrorException();
            }
        } else {
            System.out.println("ERROR: message received: "+returnedMessage);
        }
    }

    @Override
    public void lobby(String username, Long time) throws NetworkErrorException, NotValidInputException {
        String returnedMessage;
        getMessage(); //Clean buffer
        try{
            connection.sendMessage("lobby/last_access/" + time);
            returnedMessage = getMessage();
        } catch (Exception e){
            throw new NetworkErrorException();
        }
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(returnedMessage.split("\\s*/\\s*")));
        if(commands.remove(0).equals("lobby")){
            switch(commands.remove(0)){
                case "welcome":
                    mg.unlock();
                    break;
                case "last_access":
                case "invalid_command":
                    throw new NotValidInputException();
                default:
                    if(returnedMessage==null)
                        throw new NetworkErrorException();
                    else
                        view.displayGameMessage("ERROR: "+returnedMessage);
            }
        }
    }

    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try{
            connection.sendMessage(message);
        } catch (Exception e){
            throw new NetworkErrorException();
        }

    }

    public String getMessage(){
        while(!mg.readable()){
            System.out.print("");
        }
        //just for debug purpose
        String temp = mg.getMessage();
        System.out.println("message received: "+temp);
        return temp;
        //return mg.getMessage();
    }
}
