package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.Game;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.Controller.*;

public abstract class State {

    Controller controller;
    Game game;

    /**
     *creates a new {@link State}.
     */
    public State(Controller controller) {
        this.controller = controller;
        this.game = controller.getGame();
    }

    /**
     *handles the given commands for the given user.
     */
    public abstract void handleEvent(String username, ArrayList<String> commands);

    /**
     *exits the given user from the game.
     */
    public void exitGame(String username) {
        if (!controller.getOfflinePlayers().contains(username)) {
            controller.getOfflinePlayers().add(username);
        }
        controller.deleteObserver(username);
        game.notifyObservers(username + LEFT_THE_GAME);
        controller.sendMessage(username, YOU_LEFT_THE_GAME);
    }

    /**
     *join the given user to the game.
     */
    public void joinGame(String username) {
        controller.getOfflinePlayers().remove(username);
        controller.sendMessage(username, BACK_TO_GAME);
        controller.sendMessage(username, GAME_JOINED);
        controller.updateTable(username);
        game.notifyObservers(username + JOINED_THE_GAME);
        controller.addObserver(username);
    }

    /**
     *@return whether the format of the given commands is correct or not.
     */
    boolean checkFormat(ArrayList<String> commands) {
        boolean result = false;
        try {
            for (String command : commands) {
                Integer.parseInt(command);
            }
            result = true;
        } catch (NumberFormatException e) {
            //
        }
        return result;
    }
}
