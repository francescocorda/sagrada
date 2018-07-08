package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.exceptions.RollBackException;

import java.util.ArrayList;

public class RollDraftPool extends Effect {
    private static String description = "The draft pool will be rolled again.";

    /**
     * create a new {@link RollDraftPool}
     */
    public RollDraftPool() {
        this.stop = false;
        this.commandsLength = 0;
    }

    /**
     * Rolls all the dices in the draw pool an additional time in the round.
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @return true if the model manipulation made by the effect is completed correctly
     */
    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) {
       table.rollDrawPool();
       table.notifyObservers();
       return true;
    }

    /**
     * explains the effect accordingly to the given parameters.
     * @param table : the given {@link Table} table
     * @param round : the given {@link Round} round
     */
    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + description);
    }

    /**
     * @return the element of the table activated by the effect
     */
    @Override
    public String getActiveTableElement() {
        return "";
    }
}
