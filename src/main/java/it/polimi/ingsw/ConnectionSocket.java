package it.polimi.ingsw;

import java.io.*;
import java.net.Socket;

public class ConnectionSocket implements Connection {
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private Socket socket;

    ConnectionSocket(Socket socket) {
        this.socket = socket;
        connectionSocketHandler();
    }

    private void connectionSocketHandler(){
        try {
            //setup communication channels
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (Exception e) {
            System.out.println("Exception: e=" + e);
            e.printStackTrace();

            try {
                socket.close();
            } catch (Exception ex) {
            }
        }
    }

    public void sendMessage(String message){
        outSocket.println(message);
    }

    public String getMessage(){
        String message;
        try {
            message=inSocket.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            message="";
        }
        return message;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
