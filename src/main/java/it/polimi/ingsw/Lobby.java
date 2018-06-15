package it.polimi.ingsw;

import it.polimi.ingsw.Server.ServerMain;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;

public class Lobby {
        private ArrayList<VirtualView> views;
        private static Lobby instance = null;
        private ArrayList<ClientData> connectedPlayers;
        private ArrayList<Long> connectedPlayersLastTime;
        private Timer timer;
        private boolean isTimerSet = false;
        private ClientDatabase players;
        private static final String LOCATION = "lobby";
        private ArrayList<Controller> controllers;
        private int timerSeconds;

        private Lobby() {
            connectedPlayers = new ArrayList<>();
            connectedPlayersLastTime = new ArrayList<>();
            timer = new Timer();
            players = ClientDatabase.getPlayerDatabase();
            views = new ArrayList<>();
            controllers = new ArrayList<>();
            timerSeconds = ServerMain.getServerMain().getTimerSeconds();
        }

        public static synchronized Lobby getLobby() {
            if (instance == null) {
                instance = new Lobby();
            }
            return instance;
        }



        public synchronized void addPlayer(String username, long time) {
            ClientData player = players.getPlayerData(username);
            VirtualView virtualView = new VirtualView(player);
            if(reconnect(virtualView)){
                send(username, "/back_to_game");
            } else {
                views.add(virtualView);
                VirtualViewsDataBase.getVirtualViewsDataBase().addVirtualView(virtualView);
                connectedPlayers.add(player);
                connectedPlayersLastTime.add(time);
                send(username, "/welcome");
                toTerminal("player: " + player.getUsername() + " singed in");
                broadcast("/player_joined/" + player.getUsername());
                send(username, listOfPlayers());
                players.setPhase(username, Phase.LOBBY);
                trigger();
            }
        }


        public synchronized void removePlayer(ClientData player) {
            int index = connectedPlayers.indexOf(player);
            String username = connectedPlayers.get(index).getUsername();
            views.remove(index);
            connectedPlayers.remove(index);
            connectedPlayersLastTime.remove(index);
            toTerminal("User: " + player.getUsername() + " has logged out");
            broadcast("/player_left/"+username);
            trigger();
        }

        private void broadcast(String message) {
            for (ClientData player : connectedPlayers) {
                send(player.getUsername(), message);
            }
        }

        private void send(String username, String message) {
            ClientData player = players.findPlayer(username);
            ClientHandler clientHandler = player.getClientHandler();
            try {
                clientHandler.sendMessage(LOCATION + message);
            } catch (NetworkErrorException e) {
                players.disconnect(username);
                //TODO ?
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
                        broadcast("/timer_restarted");
                    }
                    break;
                case 2:
                    if (!isTimerSet) {
                        isTimerSet = true;
                        broadcast("/timer_started");
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
            ArrayList<ClientData> playerToBeChecked = new ArrayList<>(connectedPlayers);
            for(ClientData player : playerToBeChecked){
                try {
                    player.getClientHandler().check();
                } catch (NetworkErrorException e) {
                    players.disconnect(player.getUsername());
                }
            }
            return connectedPlayers.size();
        }

        private synchronized void startGame() {
            if (connectedPlayers.size() > 1) {
                isTimerSet = false;
                timer.cancel();
                toTerminal("Game start");
                broadcast("/start_game" + listOfPlayers());
                ArrayList<ClientData> playersInTheRightOrder = new ArrayList<>();
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
                for (ClientData player : playersInTheRightOrder) {
                    player.nextPhase();
                    player.getClientHandler().game();
                }
                controllers.add(new Controller(controllers.size() + 1, viewsInTheRightOrder));
                connectedPlayers = new ArrayList<>();
                connectedPlayersLastTime = new ArrayList<>();
                views = new ArrayList<>();
                notifyAll();
            } else {
                trigger();
            }
        }

        private String listOfPlayers() {
            String message = "/list_of_players";
            for (ClientData player : connectedPlayers)
                message = message.concat("/" + player.getUsername());
            return message;
        }

        private void toTerminal(String message) {
            System.out.println("Lobby: " + message);
        }

        public void removeController(Controller controller){
            controllers.remove(controller);
        }

        private boolean reconnect(VirtualView view){
            String username = view.getUsername();
            for(Controller controller : controllers){
                if(controller.contains(username)){
                    view.addObserver(controller);
                    view.notifyObservers("join");
                    return  true;
                }
            }
            return false;
        }

        public void close(){
            System.exit(0);
        }
    }
