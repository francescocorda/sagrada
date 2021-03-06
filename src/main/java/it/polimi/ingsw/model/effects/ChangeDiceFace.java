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
    private static final HashMap<ChangeFace, Integer> lenghts = lengths();

    private static final int INCREASE_FACE = 1;
    private static final int OPPOSITE_FACE = -7;

    private ChangeFace changeFace;

    /**
     *
     * @return the description associated with the {@link ChangeFace} element of the effect
     */
    static HashMap<ChangeFace, String> descriptions() {
        HashMap<ChangeFace, String> temp = new HashMap<>();
        temp.put(SEQUENTIAL, "Choose +1 to increment and -1 to decrement the dice value.");
        temp.put(OPPOSITE, "The dice will be turned on the opposite side.");
        temp.put(RANDOM, "The dice will be rolled again.");
        temp.put(CHOOSE, "Choose the value to assign to the dice.");
        return temp;
    }

    /**
     *
     * @return the boolean associated with the {@link ChangeFace} element of the effect
     */
    static HashMap<ChangeFace, Boolean> stops() {
        HashMap<ChangeFace, Boolean> temp = new HashMap<>();
        temp.put(SEQUENTIAL, true);
        temp.put(OPPOSITE, false);
        temp.put(RANDOM, false);
        temp.put(CHOOSE, true);
        return temp;
    }

    /**
     *
     * @return the command's length associated with the {@link ChangeFace} element of the effect
     */
    static HashMap<ChangeFace, Integer> lengths() {
        HashMap<ChangeFace, Integer> temp = new HashMap<>();
        temp.put(SEQUENTIAL, 1);
        temp.put(OPPOSITE, 0);
        temp.put(RANDOM, 0);
        temp.put(CHOOSE, 1);
        return temp;
    }


    /**
     * create a new {@link ChangeDiceFace} given the parameter
     * @param changeFace is the given parameter that defines the effect's behaviour
     */
    public ChangeDiceFace(ChangeFace changeFace) {
        this.changeFace = changeFace;
        this.stop = stops.get(changeFace);
        this.commandsLength = lenghts.get(changeFace);
    }


    /**
     * Changes the active dice's face accordingly to the {@link ChangeFace} attribute of the class:
     * changeFace = RANDOM --> assign a random value
     * changeFace = SEQUENTIAL --> increase or decrease the value by one, 1 can't become 6 and vice versa
     * changeFace = CHOOSE --> assign a value given the command received as a parameter
     * changeFace = OPPOSITE --> assign the opposite value
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @return true if the model manipulation made by the effect is completed correctly
     */
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

    /**
     * explains the effect accordingly to the given parameters.
     * @param table : the given {@link Table} table
     * @param round : the given {@link Round} round
     */
    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + descriptions.get(changeFace));
    }

    /**
     *
     * @return the element of the table activated by the effect
     */
    @Override
    public String getActiveTableElement() {
        return changeFace.toString();
    }
}
