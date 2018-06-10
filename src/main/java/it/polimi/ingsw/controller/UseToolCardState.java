package it.polimi.ingsw.controller;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.Controller.INVALID_FORMAT;
import static it.polimi.ingsw.controller.Controller.WAIT_YOUR_TURN;

public class UseToolCardState extends State {

    public UseToolCardState(Controller controller) {
        super(controller);
    }

    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        if (game.isCurrentPlayer(username)) {
            useToolCard(username, commands);
            if (!game.isToolCardActive()) {
                controller.checkGameState();
            }
        }  else {
            controller.sendMessage(username, WAIT_YOUR_TURN);
        }
    }

    private void useToolCard(String username, ArrayList<String> commands) {
        if (commands.size() == game.getToolCardCommandsSize() && checkFormat(commands)) {
            game.useToolCard(commands);
        } else {
            controller.sendMessage(username, INVALID_FORMAT);
        }
    }
}
