package it.polimi.ingsw.controller;

import it.polimi.ingsw.Model.Game.Game;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.Controller.INVALID_FORMAT;

public abstract class State {

    Controller controller;
    Game game;

    public State(Controller controller) {
        this.controller = controller;
        this.game = controller.getGame();
    }

    public abstract void handleEvent(String username, ArrayList<String> commands);

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
