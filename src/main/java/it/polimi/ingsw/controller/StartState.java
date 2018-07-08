package it.polimi.ingsw.controller;


import java.util.ArrayList;

import static it.polimi.ingsw.model.game.Game.PROPOSED_PATTERNS;
import static it.polimi.ingsw.controller.Controller.CHOOSE_PATTERN_CARD;
import static it.polimi.ingsw.controller.Controller.INVALID_FORMAT;
import static it.polimi.ingsw.controller.Controller.PATTERN_ASSIGNED;


public class StartState extends State {

    private static final String CHOOSE_ACTION = "CHOOSE_ACTION";
    private static final String START = "START";

    /**
     *creates a new {@link StartState}.
     */
    public StartState(Controller controller) {
        super(controller);
    }

    /**
     *handles the given commands for the given user.
     */
    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
        assignPatternCard(username, commands);
    }

    /**
     *assigns the {@link it.polimi.ingsw.model.cards.patterns.PatternCard} from the given commands to the given user.
     */
    private synchronized void assignPatternCard(String username, ArrayList<String> commands) {
        if (checkFormat(commands)) {
            int indexPattern = Integer.parseInt(commands.remove(0));
            if ((indexPattern >= 1 && indexPattern <= PROPOSED_PATTERNS) && commands.isEmpty()) {
                if (game.setPatternCard(username, indexPattern-1)) {
                    controller.sendActiveTableElement(username, START);
                    controller.sendMessage(username, PATTERN_ASSIGNED);
                }
            } else {
                controller.sendMessage(username, INVALID_FORMAT);
                controller.sendMessage(username, CHOOSE_PATTERN_CARD);
            }
            if (game.doneAssignPatternCards()) {
                controller.setState(controller.getChooseActionState());
                if (!game.isGameEnded())
                    controller.sendActiveTableElement(game.getCurrentPlayer(), CHOOSE_ACTION);
                controller.setTimerSkipTurn();
                controller.itsYourTurn();
            }
        } else {
            controller.sendMessage(username, INVALID_FORMAT);
            controller.sendMessage(username, CHOOSE_PATTERN_CARD);
        }
    }
}
