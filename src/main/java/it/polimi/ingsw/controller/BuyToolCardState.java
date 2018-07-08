package it.polimi.ingsw.controller;

import java.util.ArrayList;

import static it.polimi.ingsw.model.game.Game.TOOL_CARDS_DIMENSION;
import static it.polimi.ingsw.controller.Controller.*;

public class BuyToolCardState extends State {

    /**
     *creates a new {@link BuyToolCardState}.
     */
    public BuyToolCardState(Controller controller) {
        super(controller);
    }

    /**
     *handles the given commands for the given username.
     */
    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        if (game.isCurrentPlayer(username)) {
            buyToolCard(username, commands);
        } else {
            controller.sendMessage(username, WAIT_YOUR_TURN);
        }
    }

    /**
     *exits the given username from the game.
     */
    @Override
    public void exitGame(String username) {
        super.exitGame(username);
        if (!game.isGameEnded() && game.getCurrentPlayer().equals(username)) {
            controller.skipTurn();
        }
    }

    /**
     *make the given user to buy a toolcard.
     */
    private void buyToolCard(String username, ArrayList<String> commands) {
        if (commands.size() == 1 && commands.get(0).equals("cancel")) {
            controller.setState(controller.getChooseActionState());
            controller.sendActiveTableElement(username, "CHOOSE_ACTION");
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
                        controller.sendActiveTableElement(username, "CHOOSE_ACTION");
                    }
                } else {
                    controller.setState(controller.getChooseActionState());
                    controller.sendActiveTableElement(username, "CHOOSE_ACTION");
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
