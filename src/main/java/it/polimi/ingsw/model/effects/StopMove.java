package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;

import java.util.ArrayList;

public class StopMove extends Effect {

    private static String description = "Press [1] to proceed with the move, press [2] for NO.";

    /**
     * creates an instance of {@link StopMove}
     */
    public StopMove() {
        this.stop = true;
        this.commandsLength = 1;
    }

    /**
     * Interrupts the use of the active tool card, if the user wants to end the move before the
     * regular end.
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @return true if the given command is equal to "1" or "2"
     */
    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) {
        if (commands.get(0).equals("1")) {
            return true;
        } else if(commands.get(0).equals("2")) {
            table.getActiveToolCard().endUse();
            return true;
        }
        return false;
    }

    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + description);
    }

    @Override
    public String getActiveTableElement() {
        return "YES_NO";
    }
}
