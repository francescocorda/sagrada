package it.polimi.ingsw.client;

import it.polimi.ingsw.connection.ConnectionSocket;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.View;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicatorSocket implements Communicator {

    private View view;
    private ConnectionSocket connection;

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
    }

    @Override
    public void login(String username, String password) throws NetworkErrorException, NotValidInputException {
        String returnedMessage;
        getMessage(); //Clean buffer
        try{
            //waiting message protocol to be updated
            connection.sendMessage("login<" + username + "><" + password + ">");
            returnedMessage = getMessage();
        } catch (Exception e){
            throw new NetworkErrorException();
        }
        switch(returnedMessage){
            case "login<success>":
                break;
            case "login<failed>":
            case "login<invalid_command>":
                throw new NotValidInputException();
            default:
                System.out.println(returnedMessage);
                throw new NetworkErrorException();
        }
    }

    @Override
    public void lobby(String username, Long time) throws NetworkErrorException, NotValidInputException {
        String returnedMessage;
        getMessage(); //Clean buffer
        try{
            //waiting message protocol to be updated
            connection.sendMessage("lobby<last_access><" + time + ">");
            returnedMessage = getMessage();
        } catch (Exception e){
            throw new NetworkErrorException();
        }
        switch(returnedMessage){
            case "lobby<welcome>":
                break;
            case "lobby<last_access><invalid_time>":
            case "lobby<invalid_command>":
                throw new NotValidInputException();
            default:
                if(returnedMessage==null)
                    throw new NetworkErrorException();
                else
                    view.displayMessage(returnedMessage);
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
        String temp = connection.getMessage();
        while(temp.equals("ping")){
            connection.sendMessage("pong");
            temp = connection.getMessage();
        }
        return temp;
    }
}
