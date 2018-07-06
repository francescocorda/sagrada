package it.polimi.ingsw.server;

import it.polimi.ingsw.database.ClientData;
import it.polimi.ingsw.database.ClientDatabase;
import it.polimi.ingsw.database.Phase;
import it.polimi.ingsw.database.VirtualViewsDataBase;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.VirtualView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class Lobby implements Observer {
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
    private VirtualViewsDataBase vvdb;
    private static final String LOGOUT = "logout";
    private static final String JOIN = "/join";
    private static final String WELCOME = "/welcome";
    private static final String PLAYER = "player: ";
    private static final String SINGED_IN = " signed in";
    private static final String USER = "User: ";
    private static final String PLAYER_JOINED = "/player_joined/";
    private static final String HAS_LOGGED_OUT = " has logged out";
    private static final String PLAYER_LEFT = "/player_left/";
    private static final String TIMER_RESET = "/timer_reset";
    private static final String TIMER_STARTED = "/timer_started";
    private static final String GAME_START = "game start";
    private static final String START_GAME = "/start_game";
    private static final String LIST_OF_PLAYERS = "/list_of_players";
    private static final String SEPARATOR_SYMBOL = "/";
    private static final String LOBBY_MESSAGE_1 = "Lobby: ";
    private static final String LOBBY_MESSAGE_2 = "LOBBY:";
    private static final String PLAYERS_IN_LOBBY = "Players in lobby:";
    private static final String NUMBER_OF_GAMES = "number of games: ";
    private static final String CONTROLLER = "Controller ";
    private static final String PLAYERS_MESSAGE = "Players:";
    private static final String OFFLINE_PLAYERS = "Offline Players:";
    private static final String LEVEL_1 = " - ";
    private static final String LEVEL_2 = "   + ";
    private static final String LEVEL_3 = "     > ";

    /**
     * creates a new {@link Lobby}.
     */
    private Lobby() {
        connectedPlayers = new ArrayList<>();
        connectedPlayersLastTime = new ArrayList<>();
        timer = new Timer();
        players = ClientDatabase.getPlayerDatabase();
        views = new ArrayList<>();
        controllers = new ArrayList<>();
        timerSeconds = ServerMain.getServerMain().getTimerSeconds();
        vvdb = VirtualViewsDataBase.getVirtualViewsDataBase();
    }

    /**
     * @return the only instance of the {@link Lobby}.
     */
    public static synchronized Lobby getLobby() {
        if (instance == null) {
            instance = new Lobby();
        }
        return instance;
    }

    /**
     * adds a given player to the lobby or if it has a running game it's rejoined to it.
     * it also sets the user time accordingly to it's value:
     * if the given time is 0 than player's time becomes it's last inserted time[0 if it's still it's first time],
     * if it's not 0 then the given time becomes the player's {@link ClientData#lastTime} lastTime value.
     * @param username : the {@link String} username of the new player
     * @param time : the time given by the player
     */
    public synchronized void addPlayer(String username, long time) {
        ClientData player = players.getPlayerData(username);
        long thisTime = time;
        if (!reconnect(username)) {
            if(time == 0){
                thisTime = player.getLastTime();
            } else {
                player.setLastTime(thisTime);
            }
            VirtualView virtualView;
            if(vvdb.contains(username)){
                virtualView = vvdb.getVirtualView(username);
            } else {
                virtualView = new VirtualView(player);
            }
            views.add(virtualView);
            vvdb.addVirtualView(virtualView);
            virtualView.deleteObservers();
            virtualView.addObserver(this);
            connectedPlayers.add(player);
            connectedPlayersLastTime.add(thisTime);
            send(username, WELCOME);
            toTerminal(PLAYER + player.getUsername() + SINGED_IN);
            broadcast(PLAYER_JOINED + player.getUsername());
            broadcast(listOfPlayers());
            if (player.getPhase() == Phase.LOGIN)
                player.setPhase(Phase.LOBBY);
            trigger();
        }
    }

    /**
     * remove the player whose username equals the given {@link String} username.
     * @param username : the given {@link String} username.
     */
    public synchronized void removePlayer(String username) {
        ClientData player = players.getPlayerData(username);
        int index = connectedPlayers.indexOf(player);
        if(index<0)
            return;
        views.get(index).deleteObserver(this);
        views.remove(index);
        VirtualViewsDataBase.getVirtualViewsDataBase().removeVirtualView(username);
        connectedPlayers.remove(index);
        connectedPlayersLastTime.remove(index);
        toTerminal(USER + player.getUsername() + HAS_LOGGED_OUT);
        broadcast(PLAYER_LEFT + username);
        broadcast(listOfPlayers());
        trigger();
    }

    /**
     * sends {@link Lobby#LOCATION} + the given {@link String} message to all {@link Lobby#players}.
     * @param message : the given {@link String} message
     */
    private void broadcast(String message) {
        for (VirtualView view : views) {
            view.displayMessage(LOCATION + message);
        }
    }

    /**
     * sends {@link Lobby#LOCATION} + the given {@link String} message to the given {@link String} username.
     * @param message : the given {@link String} message
     * @param username : the given {@link String} username
     */
    private void send(String username, String message) {
        for (VirtualView view : views) {
            if (view.getUsername().equals(username)) {
                view.displayMessage(LOCATION + message);
            }
        }
    }

    /**
     * checks if a new game could be started or not.
     */
    private synchronized void trigger() {
        switch (size()) {
            case 1:
                if (isTimerSet) {
                    timer.cancel();
                    timer.purge();
                    isTimerSet = false;
                    broadcast(TIMER_RESET);
                }
                break;
            case 2:
                if (!isTimerSet) {
                    isTimerSet = true;
                    broadcast(TIMER_STARTED);
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

    /**
     * @return the number of {@link Lobby#players}.
     */
    private int size() {
        ArrayList<ClientData> playerToBeChecked = new ArrayList<>(connectedPlayers);
        for (ClientData player : playerToBeChecked) {
            try {
                player.getClientHandler().check();
            } catch (NetworkErrorException e) {
                players.disconnect(player.getUsername());
            }
        }
        return connectedPlayers.size();
    }

    /**
     * starts a new game.
     */
    private synchronized void startGame() {
        if (size() > 1) {
            isTimerSet = false;
            timer.cancel();
            toTerminal(GAME_START);
            broadcast(START_GAME + listOfPlayers());
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
                views.get(indexOfMax).deleteObserver(this);
                views.remove(indexOfMax);
            }
            for (ClientData player : playersInTheRightOrder) {
                player.nextPhase();
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

    /**
     * @return a well formatted list of {@link Lobby#players}.
     */
    private String listOfPlayers() {
        String message = LIST_OF_PLAYERS;
        for (ClientData player : connectedPlayers)
            message = message.concat(SEPARATOR_SYMBOL + player.getUsername());
        return message;
    }

    /**
     * display the given {@link String} message.
     * @param message : the given {@link String} message
     */
    private void toTerminal(String message) {
        System.out.println(LOBBY_MESSAGE_1 + message);
    }

    /**
     * removes the given {@link Controller} from {@link Lobby#controllers}.
     * @param controller : the given {@link Controller}
     */
    public void removeController(Controller controller) {
        controllers.remove(controller);
    }

    /**
     * reconnects to its running game the player's whose username match the given {@link String} username.
     * @param username : the given {@link String} username
     * @return true if there's a running game for the player, false if there's not.
     */
    private boolean reconnect(String username) {
        for (Controller controller : controllers) {
            if (controller.contains(username)) {
                VirtualView view = VirtualViewsDataBase.getVirtualViewsDataBase().getVirtualView(username);
                view.addObserver(controller);
                view.notifyObservers(username + JOIN);
                return true;
            }
        }
        return false;
    }

    /**
     * closes the {@link Lobby}.
     */
    public void close() {
        System.exit(0);
    }

    /**
     * gets a {@link String} message.
     * @param message : the given {@link String} message
     */
    @Override
    public void update(String message) {
        if (message!=null)
            handleEvent(message);
    }

    /**
     * does nothing.
     * @param o : the given {@link Observable}
     */
    @Override
    public void update(Observable o) {
        //do nothing
    }

    /**
     * handles the given {@link String} message.
     * if the message contains {@link Lobby#LOGOUT} the sending player is disconnected from the {@link Lobby}
     * @param message : the given {@link String} message
     */
    private synchronized void handleEvent(String message) {
        if (message == null)
            return;
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
        if (commands.size() != 2)
            return;
        String username = commands.remove(0);
        for (ClientData player : connectedPlayers) {
            if (player.getUsername().equals(username)) {
                String command = commands.remove(0);
                if (command.equals(LOGOUT)) {
                    players.disconnect(username);
                    VirtualViewsDataBase.getVirtualViewsDataBase().removeVirtualView(username);
                    return;
                }
            }
        }
    }

    /**
     * shows the status of the {@link Lobby}.
     */
    public void status(){

        toScreen(LOBBY_MESSAGE_2);
        toScreen(LEVEL_1+PLAYERS_IN_LOBBY);
        for(VirtualView view : views){
            toScreen(LEVEL_2+view.getUsername());
        }
        toScreen(LEVEL_1+NUMBER_OF_GAMES+controllers.size());
        int counter = 0;
        for (Controller controller : controllers){
            toScreen(LEVEL_1+CONTROLLER+counter+":");
            toScreen(LEVEL_2+PLAYERS_MESSAGE);
            for(String player : controller.getPlayers()){
                toScreen(LEVEL_3+player);
            }
            toScreen(LEVEL_2+OFFLINE_PLAYERS);
            for(String player : controller.getOFFPlayer()){
                toScreen(LEVEL_3+player);
            }
            counter++;
        }
    }

    /**
     * display the given {@link String} message.
     * @param message : the given {@link String} message
     */
    private void toScreen(String message) {
        System.out.println(message);
    }
}
