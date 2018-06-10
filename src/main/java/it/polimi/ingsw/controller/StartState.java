package it.polimi.ingsw.controller;


import it.polimi.ingsw.exceptions.NotValidInputException;

import java.util.ArrayList;

import static it.polimi.ingsw.Model.Game.Game.PROPOSED_PATTERNS;
import static it.polimi.ingsw.controller.Controller.INVALID_FORMAT;


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
            if ((indexPattern >= 0 && indexPattern < PROPOSED_PATTERNS) && commands.isEmpty()) {
                try {
                    game.setPatternCard(username, indexPattern);
                    controller.sendMessage(username, "Pattern card assigned.");
                } catch (NotValidInputException e) {
                    controller.sendMessage(username, INVALID_FORMAT);
                }
            } else {
                controller.sendMessage(username, INVALID_FORMAT);
            }
            if (game.doneAssignPatternCards()) {
                controller.setTimerSkipTurn();
                controller.itsYourTurn();
                controller.setState(controller.getChooseActionState());
            }
        }
    }
}
