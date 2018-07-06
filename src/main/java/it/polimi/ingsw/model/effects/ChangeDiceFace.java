package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.exceptions.InvalidFaceException;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.model.game.Game.INVALID_MOVE_BY_PLAYER;
import static it.polimi.ingsw.model.effects.ChangeFace.*;
import static it.polimi.ingsw.model.effects.RemoveFrom.INVALID_COORDINATES;
import static java.lang.Math.abs;


public class ChangeDiceFace extends Effect {

    private static final HashMap<ChangeFace, String> descriptions = descriptions();
    private static final HashMap<ChangeFace, Boolean> stops = stops();
    private static final HashMap<ChangeFace, Integer> lenghts = lenghts();

    private static final int INCREASE_FACE = 1;
    private static final int OPPOSITE_FACE = -7;

    private ChangeFace changeFace;

    static HashMap<ChangeFace, String> descriptions() {
        HashMap<ChangeFace, String> temp = new HashMap<>();
        temp.put(SEQUENTIAL, "Choose +1 to increment and -1 to decrement the dice value.");
        temp.put(OPPOSITE, "The dice will be turned on the opposite side.");
        temp.put(RANDOM, "The dice will be rolled again.");
        temp.put(CHOOSE, "Choose the value to assign to the dice.");
        return temp;
    }

    static HashMap<ChangeFace, Boolean> stops() {
        HashMap<ChangeFace, Boolean> temp = new HashMap<>();
        temp.put(SEQUENTIAL, true);
        temp.put(OPPOSITE, false);
        temp.put(RANDOM, false);
        temp.put(CHOOSE, true);
        return temp;
    }

    static HashMap<ChangeFace, Integer> lenghts() {
        HashMap<ChangeFace, Integer> temp = new HashMap<>();
        temp.put(SEQUENTIAL, 1);
        temp.put(OPPOSITE, 0);
        temp.put(RANDOM, 0);
        temp.put(CHOOSE, 1);
        return temp;
    }


    public ChangeDiceFace(ChangeFace changeFace) {
        this.changeFace = changeFace;
        this.stop = stops.get(changeFace);
        this.commandsLenght = lenghts.get(changeFace);
    }



    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) {

        int number = 0;

        if (changeFace == RANDOM) {
            table.getActiveDice().roll();
            table.notifyObservers();
            return true;
        }else if(changeFace == SEQUENTIAL) {
            String command = commands.remove(0);
            if(command.equals("+1")) {
                number = table.getActiveDice().valueOf() + INCREASE_FACE;
            }
            else if(command.equals("-1")) {
                number = table.getActiveDice().valueOf() - INCREASE_FACE;
            }
        } else if(changeFace == CHOOSE) {
            number = Integer.parseInt(commands.remove(0));

        }else if(changeFace == OPPOSITE) {
            number = abs(table.getActiveDice().valueOf() + OPPOSITE_FACE);
        }
        try {
            table.getActiveDice().setFace(number);
            table.notifyObservers();
            return true;
        } catch (InvalidFaceException e) {
            table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() + ":\n" +
                    INVALID_COORDINATES);
        }
        return false;
    }


    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + descriptions.get(changeFace));
    }

    @Override
    public String getActiveTableElement() {
        return changeFace.toString();
    }
}
