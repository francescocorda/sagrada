package it.polimi.ingsw.controller;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.Controller.ACTION_CANCELED;
import static it.polimi.ingsw.controller.Controller.INVALID_FORMAT;
import static it.polimi.ingsw.controller.Controller.WAIT_YOUR_TURN;

public class MoveState extends State {

    /**
     *creates a new {@link MoveState}
     */
    public MoveState(Controller controller) {
        super(controller);
    }

    /**
     *handles given commands for the given user.
     */
    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        if (game.isCurrentPlayer(username)) {
                if (commands.size()==1 && commands.get(0).equals("cancel")) {
                    game.cancelMove();
                    controller.setState(controller.getChooseActionState());
                    controller.sendActiveTableElement(username, "CHOOSE_ACTION");
                    controller.sendMessage(username, ACTION_CANCELED);
                    controller.itsYourTurn();
                } else {
                    performMove(username, commands);
                    if (!game.isMoveActive()) {
                        controller.checkGameState();
                    } else {
                        controller.sendActiveTableElement(username);
                    }
                }
        } else {
            controller.sendMessage(username, WAIT_YOUR_TURN);
        }
    }

    /**
     * performs the given commands for the given user.
     */
    public void performMove(String username, ArrayList<String> commands) {
        if (commands.size() == game.getMoveCommandsSize() && checkFormat(commands)) {
            game.performMove(commands);
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
