package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.Game;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.Controller.*;

public abstract class State {

    Controller controller;
    Game game;

    public State(Controller controller) {
        this.controller = controller;
        this.game = controller.getGame();
    }

    public abstract void handleEvent(String username, ArrayList<String> commands);

    public void exitGame(String username) {
        if (!controller.getOfflinePlayers().contains(username)) {
            controller.getOfflinePlayers().add(username);
        }
        controller.deleteObserver(username);
        game.notifyObservers(username + LEFT_THE_GAME);
        controller.sendMessage(username, YOU_LEFT_THE_GAME);
    }

    public void joinGame(String username) {
        controller.getOfflinePlayers().remove(username);
        controller.sendMessage(username, BACK_TO_GAME);
        controller.sendMessage(username, GAME_JOINED);
        controller.updateTable(username);
        game.notifyObservers(username + JOINED_THE_GAME);
        controller.addObserver(username);
    }

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
