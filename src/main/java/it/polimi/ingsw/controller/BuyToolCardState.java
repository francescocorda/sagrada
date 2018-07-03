package it.polimi.ingsw.controller;

import java.util.ArrayList;

import static it.polimi.ingsw.Model.Game.Game.TOOL_CARDS_DIMENSION;
import static it.polimi.ingsw.controller.Controller.*;

public class BuyToolCardState extends State {

    public BuyToolCardState(Controller controller) {
        super(controller);
    }

    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        if (game.isCurrentPlayer(username)) {
            buyToolCard(username, commands);
        } else {
            controller.sendMessage(username, WAIT_YOUR_TURN);
        }
    }

    @Override
    public void exitGame(String username) {
        super.exitGame(username);
        if (!game.isGameEnded() && game.getCurrentPlayer().equals(username)) {
            controller.skipTurn();
        }
    }

    private void buyToolCard(String username, ArrayList<String> commands) {
        if (commands.size() == 1 && commands.get(0).equals("cancel")) {
            controller.setState(controller.getChooseActionState());
            controller.sendMessage(username, ACTION_CANCELED);
            controller.itsYourTurn();
        } else if(commands.size() == 1 && checkFormat(commands)) {
            int index = Integer.parseInt(commands.remove(0));
            if ((index >= 1 && index <= TOOL_CARDS_DIMENSION)) {
                if (game.toolCardUseAllowed(index-1) && game.buyToolCard(index-1)) {
                    game.useToolCard(new ArrayList<>());
                    if (game.isToolCardActive()) {
                        controller.sendActiveTableElement(username);
                        controller.setState(controller.getUseToolCardState());
                    } else {
                        controller.itsYourTurn();
                        controller.setState(controller.getChooseActionState());
                    }
                } else {
                    controller.setState(controller.getChooseActionState());
                    controller.itsYourTurn();
                }
            } else {
                controller.sendMessage(username, INVALID_FORMAT);
                controller.sendMessage(username, CHOOSE_TOOL_CARD);
            }
        } else {
            controller.sendMessage(username, INVALID_FORMAT);
            controller.sendMessage(username, CHOOSE_TOOL_CARD);
        }
    }
}
