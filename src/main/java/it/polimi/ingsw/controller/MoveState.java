package it.polimi.ingsw.controller;

import java.util.ArrayList;

import static it.polimi.ingsw.controller.Controller.INVALID_FORMAT;
import static it.polimi.ingsw.controller.Controller.WAIT_YOUR_TURN;

public class MoveState extends State {

    public MoveState(Controller controller) {
        super(controller);
    }

    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        if (game.isCurrentPlayer(username)) {
            if (game.moveAllowed()) {
                performMove(username, commands);
                if (!game.isMoveActive()) {
                    controller.checkGameState();
                }
            } else {
                controller.setState(controller.getChooseActionState());
                controller.itsYourTurn();
            }
        } else {
            controller.sendMessage(username, WAIT_YOUR_TURN);
        }
    }

    public void performMove(String username, ArrayList<String> commands) {
        if (commands.size() == game.getMoveCommandsSize() && checkFormat(commands)) {
            game.performMove(commands);
        } else {
            controller.sendMessage(username, INVALID_FORMAT);
        }
    }
}
