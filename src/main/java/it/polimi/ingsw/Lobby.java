package it.polimi.ingsw;

import it.polimi.ingsw.Server.ServerMain;
import it.polimi.ingsw.connection.ConnectionMode;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.view.VirtualView;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Lobby {

        private ArrayList<VirtualView> views;
        private static Lobby instance = null;
        private ArrayList<PlayerData> connectedPlayers;
        private ArrayList<Long> connectedPlayersLastTime;
        private Timer timer;
        private boolean isTimerSet = false;
        private PlayerDatabase players;
        private static final String LOCATION = "lobby";
        ArrayList<Controller> games;
        private int timerSeconds;

        private Lobby() {
            connectedPlayers = new ArrayList<>();
            connectedPlayersLastTime = new ArrayList<>();
            timer = new Timer();
            players = PlayerDatabase.getPlayerDatabase();
            views = new ArrayList<>();
            games = new ArrayList<>();
            timerSeconds = ServerMain.getServerMain().getTimerSeconds();
        }

        public static synchronized Lobby getLobby() {
            if (instance == null) {
                instance = new Lobby();
            }
            return instance;
        }



        public synchronized void addPlayer(String username, long time) {
            PlayerData player = players.getPlayerData(username);
            VirtualView virtualView = new VirtualView(player);
            views.add(virtualView);
            VirtualViewsDataBase.getVirtualViewsDataBase().addVirtualView(virtualView);
            connectedPlayers.add(player);
            connectedPlayersLastTime.add(time);
            toTerminal("player: " + player.getUsername() + " singed in");
            broadcast("<player_joined><" + player.getUsername() + ">");
            send(username, "<welcome>");
            send(username, listOfPlayers());
            players.setPhase(username, Phase.LOBBY);
            trigger();
        }


        public synchronized void removePlayer(PlayerData player) {
            int index = connectedPlayers.indexOf(player);
            String username = connectedPlayers.get(index).getUsername();
            views.remove(index);
            connectedPlayers.remove(index);
            connectedPlayersLastTime.remove(index);
            toTerminal("User: " + player.getUsername() + " has logged out");
            broadcast("<player_left><" + username + ">");
            trigger();

        }

        private void broadcast(String message) {
            for (PlayerData player : connectedPlayers) {
                try {
                    if (player.getCurrentConnectionMode() == ConnectionMode.RMI) {
                        //broadcast for RMI Clients
                        players.getClientRMI(player.getUsername()).send(LOCATION + message);
                    } else {
                        //broadcast for Socket Clients
                        player.getClientSocket().sendMessage(LOCATION + message);
                    }
                } catch (RemoteException | NotFound e) {
                    //Disconnection already handled.
                }
            }
        }

        public void send(String username, String message) {  //To be DEFINE for socket clients
            PlayerData player = players.findPlayer(username);
            try {
                if (player.getCurrentConnectionMode() == ConnectionMode.RMI) {
                    players.getClientRMI(username).send(LOCATION + message);
                } else {
                    player.getClientSocket().sendMessage(LOCATION + message);
                }
            } catch (RemoteException | NotFound e) {
                //Disconnection already handled.
            }
        }

        private synchronized void trigger() {
            switch (size()) {
                case 1:
                    if (isTimerSet) {
                        timer.cancel();
                        timer.purge();
                        try {
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    startGame();
                                }
                            }, (long) timerSeconds * 1000);
                        } catch (IllegalStateException e) {
                            toTerminal("Error: timer already cancelled");
                        }
                        broadcast("<timer_restarted>");
                    }
                    break;
                case 2:
                    if (!isTimerSet) {
                        isTimerSet = true;
                        broadcast("<timer_started>");
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                startGame();
                            }
                        }, (long) timerSeconds * 1000);
                    }
                    break;
                case 4:
                    startGame();
                    break;
                default:
                    break;
            }
        }


        private int size() {
            ArrayList<PlayerData> playerToBeChecked = new ArrayList<>(connectedPlayers);
            for(PlayerData player : playerToBeChecked){
                try {
                    if (player.getCurrentConnectionMode() == ConnectionMode.RMI) {
                        players.getClientRMI(player.getUsername()).checkConnection();
                    } else {
                        players.getClientSocket(player.getUsername()).isOnline();
                    }
                } catch (RemoteException | NotFound e){
                    if(player.getCurrentConnectionMode() == ConnectionMode.RMI)
                        players.removeRMIClient(player.getUsername());
                    //Disconnection Socket already handled
                }
            }
            return connectedPlayers.size();
        }

        private synchronized void startGame() {
            if (connectedPlayers.size() > 1) {
                isTimerSet = false;
                timer.cancel();
                toTerminal("game start");
                broadcast("<start_game>" + listOfPlayers());
                ArrayList<PlayerData> playersInTheRightOrder = new ArrayList<>();
                ArrayList<VirtualView> viewsInTheRightOrder = new ArrayList<>();
                while (!connectedPlayersLastTime.isEmpty()) {
                    int indexOfMax = 0;
                    for (Long lastTimeVisit : connectedPlayersLastTime) {
                        if (lastTimeVisit > connectedPlayersLastTime.get(indexOfMax)) {
                            indexOfMax = connectedPlayersLastTime.indexOf(lastTimeVisit);
                        }
                    }
                    playersInTheRightOrder.add(connectedPlayers.get(indexOfMax));
                    viewsInTheRightOrder.add(views.get(indexOfMax));
                    connectedPlayers.remove(indexOfMax);
                    connectedPlayersLastTime.remove(indexOfMax);
                    views.remove(indexOfMax);
                }
                for (PlayerData player : playersInTheRightOrder) {
                    player.nextPhase();
                }
                games.add(new Controller(games.size() + 1, viewsInTheRightOrder));
                connectedPlayers = new ArrayList<>();
                connectedPlayersLastTime = new ArrayList<>();
                views = new ArrayList<>();
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

        private void toTerminal(String message) {
            System.out.println("Lobby: " + message);
        }
    }
