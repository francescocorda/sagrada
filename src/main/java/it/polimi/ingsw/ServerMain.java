package it.polimi.ingsw;

public class ServerMain {

    private static boolean SERVER_UP;
    private static SocketServer serverSoket;
    private static int socketServerPort;

    public static void main( String[] args ) {


        Dice dice = new Dice(Color.ANSI_BLUE);

        System.out.println("Hello World!" + dice);
        dice.setColor(Color.ANSI_PURPLE);
        dice.dump();

        DiceBag bag = new DiceBag();
        bag.dump();
        System.out.println("--------");
        for (int i = 0; i < 90; i++) {
            Dice d = bag.draw();
            d.roll();
            System.out.println("d: " + d);
            //d.dump();
            bag.dump();
        }
        //Initialize all the structure for the game/server
        SERVER_UP=true;
        socketServerPort=3000;
        Lobby.getLobby();
        start();
        ServerMain server = new ServerMain();
    }


    private static void start(){
        serverSoket = new SocketServer(socketServerPort);
    }

    public static boolean getStatus(){
        return SERVER_UP;
    }
}
