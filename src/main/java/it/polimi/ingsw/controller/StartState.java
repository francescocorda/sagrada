package it.polimi.ingsw.controller;


import java.util.ArrayList;

import static it.polimi.ingsw.Model.Game.Game.PROPOSED_PATTERNS;
import static it.polimi.ingsw.controller.Controller.CHOOSE_PATTERN_CARD;
import static it.polimi.ingsw.controller.Controller.INVALID_FORMAT;
import static it.polimi.ingsw.controller.Controller.PATTERN_ASSIGNED;


public class StartState extends State {

    public StartState(Controller controller) {
        super(controller);
    }

    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        assignPatternCard(username, commands);
    }

    private synchronized void assignPatternCard(String username, ArrayList<String> commands) {
        if (checkFormat(commands)) {
            int indexPattern = Integer.parseInt(commands.remove(0));
            if ((indexPattern >= 1 && indexPattern <= PROPOSED_PATTERNS) && commands.isEmpty()) {
                if (game.setPatternCard(username, indexPattern-1)) {
                    controller.sendActiveTableElement(username, "START");
                    controller.sendMessage(username, PATTERN_ASSIGNED);
                }
            } else {
                controller.sendMessage(username, INVALID_FORMAT);
                controller.sendMessage(username, CHOOSE_PATTERN_CARD);
            }
            if (game.doneAssignPatternCards()) {
                controller.setState(controller.getChooseActionState());
                if (!game.isGameEnded())
                    controller.sendActiveTableElement(game.getCurrentPlayer(), "CHOOSE_ACTION");
                controller.setTimerSkipTurn();
                controller.itsYourTurn();
            }
        } else {
            controller.sendMessage(username, INVALID_FORMAT);
            controller.sendMessage(username, CHOOSE_PATTERN_CARD);
        }
    }
}
