package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Lobby {

    private static Lobby instance = null;
    private ArrayList<ClientController> connectedPlayers;
    private ArrayList<Long> connectedPlayersLastTime;
    private Timer timer;
    private boolean isTimerSet = false;

    private Lobby() {
        connectedPlayers = new ArrayList<>();
        connectedPlayersLastTime = new ArrayList<>();
        timer = new Timer();
    }

    public static synchronized Lobby getLobby() {
        if (instance == null) {
            instance = new Lobby();
        }
        return instance;
    }

    public void joinLobby(ClientController player) {
        final String invalidCommand = "lobby<invalid_command>";
        long systemTime = System.currentTimeMillis()/1000; //current unix time in seconds
        String tempMessage;
        while (true) {
            player.sendMessage("lobby<last_access><insert_last_access>");
            MessageReader messageReader = player.getMessage();
            if (messageReader.hasNext()) {
                tempMessage = messageReader.getNext();
                if (tempMessage.equals("last_access")) {
                    if (messageReader.hasNext()) {
                        String timeString = messageReader.getNext();
                        boolean isValid = true;
                        long time = 0;
                        try {
                            time = Long.parseLong(timeString);
                        } catch (NumberFormatException e) {
                            isValid = false;
                        }
                        if (isValid && systemTime > time) {
                            addPlayer(player, time);
                            player.sendMessage("lobby<welcome>");
                            break;
                        } else {
                            player.sendMessage("lobby<last_access><invalid_time>");
                        }
                    } else {
                        player.sendMessage(invalidCommand);
                    }
                } else {
                    if (tempMessage.equals("quit"))
                        break;
                    else
                        player.sendMessage(invalidCommand);
                }
            } else {
                player.sendMessage(invalidCommand);
            }
        }
    }

    private synchronized void addPlayer(ClientController player, long time) {
        connectedPlayers.add(player);
        connectedPlayersLastTime.add(time);
        toTerminal("player: "+player.getUsername()+" singed in");
        broadcast("lobby<player_joined><" + player.getUsername() + ">");
        player.sendMessage(listOfPlayers());
        trigger();
        notifyAll();
    }

    public synchronized void removePlayer(ClientController player) {
        int index = connectedPlayers.indexOf(player);
        String username = connectedPlayers.get(index).getUsername();
        connectedPlayers.remove(index);
        connectedPlayersLastTime.remove(index);
        toTerminal("User: "+player.getUsername()+" has logged out");
        broadcast("lobby<player_left><" + username + ">");
        trigger();
        notifyAll();
    }

    private void broadcast(String message) {
        for (ClientController player : connectedPlayers) {
            player.sendMessage(message);
        }
    }

    private void trigger() {
        switch (size()) {
            case 1:
                if (isTimerSet) {
                    timer.cancel();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startGame();
                        }
                    }, (long) 2 * 60 * 1000);
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
        for(ClientController clientController : connectedPlayers){
            if(!clientController.isOnline()){
                if(connectedPlayers.size() == 1){
                    removePlayer(clientController);
                    break;
                } else {
                    removePlayer(clientController);
                }
            }
        }
        return connectedPlayers.size();
    }

    private synchronized void startGame() {
        if (size() > 1) {
            isTimerSet = false;
            timer.cancel();
            toTerminal("game start");
            broadcast("lobby<start_game>");
            ArrayList<ClientController> playersInTheRightOrder = new ArrayList<>();
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
        String message = "lobby<list_of_players>";
        for (ClientController player : connectedPlayers)
            message = message.concat("<" + player.getUsername() + ">");
        return message;
    }

    private void toTerminal(String  message){
        System.out.println("Lobby: "+message);
    }
}
