package it.polimi.ingsw.controller;

import it.polimi.ingsw.Model.Game.Game;

import java.util.ArrayList;

public abstract class State {

    Controller controller;
    Game game;

    public State(Controller controller) {
        this.controller = controller;
        this.game = controller.getGame();
    }

    public abstract void handleEvent(String username, ArrayList<String> commands);

    boolean checkFormat(ArrayList<String> commands) {
        try {
            for (String command : commands) {
                Integer.parseInt(command);
            }
            return true;
        } catch (NumberFormatException e) {
            //
        }
        return false;
    }
}
