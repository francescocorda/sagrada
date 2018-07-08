package it.polimi.ingsw.controller;

import it.polimi.ingsw.database.ClientDatabase;
import it.polimi.ingsw.database.Phase;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

public class EndState extends State {

    /**
     *creates a new {@link EndState}.
     */
    public EndState(Controller controller) {
        super(controller);
    }

    /**
     * handles the End Game Event: starts a new game if the selection is "play",
     * disconnects the player if the selection is "logout"
     * @param commands is the given {@link ArrayList<String>} of received commands
     */
    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        if (commands.size() == 1) {
            VirtualView playerView = null;
            if (controller.getPlayers().contains(username)) {
                for(VirtualView view : controller.getViews()){
                    if(view.getUsername().equals(username)){
                        playerView = view;
                    }
                }
                if (commands.get(0).equals("logout")) {
                    ClientDatabase.getPlayerDatabase().disconnect(username);
                    controller.handleOfflinePlayer(username);
                } else if (commands.get(0).equals("play")) {
                    Lobby lobby = Lobby.getLobby();
                    if (playerView != null) {
                        playerView.deleteObservers();
                        playerView.addObserver(lobby);
                    }
                    lobby.addPlayer(username, ClientDatabase.getPlayerDatabase().getPlayerLastTime(username));
                    ClientDatabase.getPlayerDatabase().getPlayerData(username).setPhase(Phase.LOBBY);
                    controller.getPlayers().remove(username);
                    controller.getViews().remove(playerView);
                }
            }
        }
    }
}
