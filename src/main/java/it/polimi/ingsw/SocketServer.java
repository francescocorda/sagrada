package it.polimi.ingsw;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class SocketServer {

    private static int PORT;
    private PlayerData players;
    private ServerSocket serverSocket;
    private int clientCounter=0;


    SocketServer(int PORT)
    {
        this.PORT = PORT;
        serverSocket = null;
        Socket socket;
        players = new PlayerData();
        try {
            serverSocket = new java.net.ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            System.out.println("\nSocketServer waiting for client on port " +  serverSocket.getLocalPort());

            // server infinite loop
            while(ServerMain.getStatus()) {
                socket = serverSocket.accept();
                clientCounter++;
                System.out.println("\nClient number: "+clientCounter+" has connected");
                Connection connection = new ConnectionSocket(socket);
                Runnable client = new ClientHandler(connection,ConnectionMode.SOCKET, players);
                new Thread(client).start();
            }
        } catch(Exception e) {
            System.out.println(e);
            try {
                serverSocket.close();
            } catch(NullPointerException ex) {
                System.out.println(ex);
            } catch ( IOException ioe){
                System.out.println(ioe);
            }
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
