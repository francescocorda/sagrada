package it.polimi.ingsw.controller;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.Controller.CHOOSE_TOOL_CARD;
import static it.polimi.ingsw.controller.Controller.INVALID_FORMAT;
import static it.polimi.ingsw.controller.Controller.WAIT_YOUR_TURN;

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

    private void buyToolCard(String username, ArrayList<String> commands) {
        if(commands.size() == 1 && checkFormat(commands)) {
            int index = Integer.parseInt(commands.remove(0));
            if (game.toolCardUseAllowed(index) && game.buyToolCard(index)) {
                game.useToolCard(new ArrayList<>());
                if (game.isToolCardActive()) {
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
    }
}
