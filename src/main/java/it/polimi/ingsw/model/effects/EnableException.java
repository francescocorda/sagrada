package it.polimi.ingsw.model.effects;


import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.model.effects.RestrictionException.*;

public class EnableException extends Effect{


    private static final HashMap<RestrictionException, String> descriptions = descriptions();

    /**
     * @return the description associated with the {@link RestrictionException} element of the effect
     */
    static HashMap<RestrictionException, String> descriptions() {
        HashMap<RestrictionException, String> temp = new HashMap<>();
        temp.put(COLOR, "The color restrictions in the window will be ignored in the next placement.");
        temp.put(FACE, "The number restrictions in the window will be ignored in the next placement.");
        temp.put(POSITION, "The position restrictions in the window will be ignored in the next placement.");
        return temp;
    }

    private RestrictionException exception;

    /**
     * create a new {@link EnableException} given the parameter
     * @param exception is the given parameter that defines the effect's behaviour
     */
    public EnableException(RestrictionException exception) {
        this.exception = exception;
        this.stop = false;
        this.commandsLength = 0;
    }

    /**
     * Enable an exception in the window dice placement accordingly to the {@link RestrictionException}
     * attribute of the class:
     * exception = COLOR --> enable color exception
     * exception = FACE --> enable face exception
     * exception = POSITION --> enable position exception
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @return true if the model manipulation made by the effect is completed correctly
     */
    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) {
        round.getCurrentPlayer().getWindowFrame().enableException(exception.getException());
        return true;
    }

    /**
     * explains the effect accordingly to the given parameters.
     * @param table : the given {@link Table} table
     * @param round : the given {@link Round} round
     */
    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + descriptions.get(exception));
    }

    /**
     * explains the effect accordingly to the given parameters.
     * @return the element of the table activated by the effect
     */
    @Override
    public String getActiveTableElement() {
        return exception.toString();
    }
}
