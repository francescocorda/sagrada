package it.polimi.ingsw;

import it.polimi.ingsw.connection.ConnectionMode;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Lobby {

    private static Lobby instance = null;
    private ArrayList<PlayerData> connectedPlayers;
    private ArrayList<Long> connectedPlayersLastTime;
    private Timer timer;
    private boolean isTimerSet = false;
    private PlayerDatabase players;

    private Lobby() {
        connectedPlayers = new ArrayList<>();
        connectedPlayersLastTime = new ArrayList<>();
        timer = new Timer();
        players = PlayerDatabase.getPlayerDatabase();
    }

    public static synchronized Lobby getLobby() {
        if (instance == null) {
            instance = new Lobby();
        }
        return instance;
    }

    public synchronized void addPlayer(String username, long time) {
        PlayerData player = players.getPlayerData(username);
        connectedPlayers.add(player);
        connectedPlayersLastTime.add(time);
        toTerminal("player: "+player.getUsername()+" singed in");
        broadcast("lobby<player_joined><" + player.getUsername() + ">");
        send(username, listOfPlayers());
        notifyAll();
        PlayerDatabase.getPlayerDatabase().nextPhase(username);
        trigger();
    }


    public synchronized void removePlayer(PlayerData player) {  //remove player only from connectedPlayers. This method is called from removeRMIClient in PlayerDatabase
        int index = connectedPlayers.indexOf(player);
        String username = connectedPlayers.get(index).getUsername();
        connectedPlayers.remove(index);
        connectedPlayersLastTime.remove(index);
        toTerminal("User: "+player.getUsername()+" has logged out");
        broadcast("lobby<player_left><" + username + ">");
        trigger();
        notifyAll();
    }

    private void broadcast(String message) {  //To be DEFINE for socket clients
        for (PlayerData player : connectedPlayers) {
            //broadcast for all RMI Clients
            if(players.getClientsRMI().containsKey(player.getUsername())){
                try {
                    players.getClientsRMI().get(player.getUsername()).send(message);
                } catch (RemoteException e) {
                    //Disconnection already handled.
                }
            }
        }
    }

    public void send(String username, String message){  //To be DEFINE for socket clients
        if(players.findPlayer(username).getCurrentConnectionMode()== ConnectionMode.RMI){
            try {
                players.getClient(username).send(message);
                } catch (RemoteException | NotFound e) {
                    //Disconnection already handled.
                }
        }
    }
    private void trigger() {
        switch (PlayerDatabase.getPlayerDatabase().sizeLobby()) {
            case 1:
                if (isTimerSet) {
                    timer.cancel();
                    timer.purge();
                    try {
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                startGame();
                            }
                        }, (long) 2 * 60 * 1000);
                    }catch (IllegalStateException e){
                        toTerminal("Error: timer already cancelled");
                    }
                    broadcast("lobby<timer_restarted>");
                }
                break;
            case 2:
                if (!isTimerSet) {
                    isTimerSet = true;
                    broadcast("lobby<timer_started>");
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startGame();
                        }
                    }, (long) 2 * 60 * 1000);
                }
                break;
            case 4:
                startGame();
                break;
            default:
                break;
        }
    }

    private int size(){
        return connectedPlayers.size();
    }

    private synchronized void startGame() {
        if (connectedPlayers.size() > 1) {
            ArrayList<PlayerData> gamePlayers = PlayerDatabase.getPlayerDatabase().getGamePlayers();
            isTimerSet = false;
            timer.cancel();
            toTerminal("game start");
            broadcast("lobby<start_game>"+listOfPlayers());
            ArrayList<PlayerData> playersInTheRightOrder = new ArrayList<>();
            while (!connectedPlayersLastTime.isEmpty()) {
                int indexOfMax = 0;
                for (Long lastTimeVisit : connectedPlayersLastTime) {
                    if (lastTimeVisit > connectedPlayersLastTime.get(indexOfMax)) {
                        indexOfMax = connectedPlayersLastTime.indexOf(lastTimeVisit);
                    }
                }
                playersInTheRightOrder.add(connectedPlayers.get(indexOfMax));
                connectedPlayers.remove(indexOfMax);
                connectedPlayersLastTime.remove(indexOfMax);
            }
            //Games.add(new GameController(playersInTheRightOrder));
            notifyAll();
        } else {
            trigger();
        }
    }

    private String listOfPlayers() {
        String message = "<list_of_players>";
        for (PlayerData player : connectedPlayers)
            message = message.concat("<" + player.getUsername() + ">");
        return message;
    }

    private void toTerminal(String  message){
        System.out.println("Lobby: "+message);
    }
}
