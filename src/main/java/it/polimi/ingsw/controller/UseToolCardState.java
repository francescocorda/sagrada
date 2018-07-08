package it.polimi.ingsw.controller;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.Controller.ACTION_CANCELED;
import static it.polimi.ingsw.controller.Controller.INVALID_FORMAT;
import static it.polimi.ingsw.controller.Controller.WAIT_YOUR_TURN;

public class UseToolCardState extends State {

    private static final String CHOOSE_ACTION = "CHOOSE_ACTION";
    private static final String CANCEL = "cancel";

    /**
     * creates a new {@link UseToolCardState}
     */
    public UseToolCardState(Controller controller) {
        super(controller);
    }

    /**
     *handles the given commands for the given user.
     */
    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        if (game.isCurrentPlayer(username)) {
            if (commands.size()==1 && commands.get(0).equals(CANCEL)) {
                game.cancelToolCardUse();
                controller.setState(controller.getChooseActionState());
                controller.sendActiveTableElement(username, CHOOSE_ACTION);
                controller.sendMessage(username, ACTION_CANCELED);
                controller.itsYourTurn();
            } else {
                useToolCard(username, commands);
                if (!game.isToolCardActive()) {
                    controller.checkGameState();
                } else {
                    controller.sendActiveTableElement(username);
                }
            }
        }  else {
            controller.sendMessage(username, WAIT_YOUR_TURN);
        }
    }

    /**
     *uses the given {@link it.polimi.ingsw.model.cards.toolcard.ToolCard} from the given commands for the given user.
     */
    private void useToolCard(String username, ArrayList<String> commands) {
        if (commands.size() == game.getToolCardCommandsSize() && checkFormat(commands)) {
            game.useToolCard(commands);
        } else {
            controller.sendMessage(username, INVALID_FORMAT);
        }
    }

    /**
     *exits the given user from the game.
     */
    @Override
    public void exitGame(String username) {
        super.exitGame(username);
        if (!game.isGameEnded() && game.getCurrentPlayer().equals(username)) {
            controller.skipTurn();
        }
    }
}
