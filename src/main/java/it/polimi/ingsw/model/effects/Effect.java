package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.exceptions.ImpossibleMoveException;
import it.polimi.ingsw.exceptions.RollBackException;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Effect implements Serializable {

    boolean stop;
    int commandsLength;

    /**
     *
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @throws ImpossibleMoveException if the combination of commands given by the controller or the current state
     * of the game leads to a state that do not allow any proceeding
     * @return true if the model manipulation made by the effect is completed correctly
     */
    public abstract boolean applyEffect(ArrayList<String> commands, Table table, Round round) throws RollBackException, ImpossibleMoveException;

    /**
     * explains the effect accordingly to the given parameters.
     * @param table : the given {@link Table} table
     * @param round : the given {@link Round} round
     */
    public abstract void explainEffect(Table table, Round round);

    /**
     *
     * @return true if the effect is a stop
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * @return the number of input parameters that the effect needs to be used.
     */
    public int getCommandsLength() {
        return commandsLength;
    }

    /**
     * @return the element of the table activated by the effect
     */
    public abstract String getActiveTableElement();
}
