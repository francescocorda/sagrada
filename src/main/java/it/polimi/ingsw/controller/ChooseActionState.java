package it.polimi.ingsw.controller;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.Controller.*;

public class ChooseActionState extends State {

    public ChooseActionState(Controller controller) {
        super(controller);
    }

    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        if (game.isCurrentPlayer(username)) {
            if (commands.size() == CHOOSE_ACTION_DIM) {
                switch (commands.remove(0)) {
                    case "move":
                        if(game.moveAllowed()) {
                            game.createMove();
                            controller.sendActiveTableElement(username);
                            controller.setState(controller.getMoveState());
                        } else {
                            controller.itsYourTurn();
                        }
                        break;
                    case "skip":
                        controller.skipTurn();
                        break;
                    case "toolcard":
                        if (!game.isToolCardUsed()) {
                            controller.sendMessage(username, CHOOSE_TOOL_CARD);
                            controller.setState(controller.getBuyToolCardState());
                        } else {
                            controller.itsYourTurn();
                        }
                        break;
                    default:
                        controller.sendMessage(username, INVALID_FORMAT);
                        break;
                }
            } else {
                controller.sendMessage(username, INVALID_FORMAT);
            }
        } else {
            controller.sendMessage(username, WAIT_YOUR_TURN);
        }
    }

    @Override
    public void exitGame(String username) {
        super.exitGame(username);
        if (game.getCurrentPlayer().equals(username)) {
            controller.skipTurn();
        }
    }
}
