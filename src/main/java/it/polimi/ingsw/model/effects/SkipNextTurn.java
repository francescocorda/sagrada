package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.exceptions.RollBackException;

import java.util.ArrayList;

public class SkipNextTurn extends Effect {
    private static String description = "It's possible to make an additional placement in this turn." +
            "\nThe next turn will be skipped.";

    /**
     * creates a new {@link SkipNextTurn}
     */
    public SkipNextTurn() {
        this.stop = false;
        this.commandsLength = 0;
    }

    /**
     * The effect skips the next turn of the player in the current Round.
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @return true if the model manipulation made by the effect is completed correctly
     * @throws RollBackException if the combination of commands given by the caller leads to a state that violates the
     * logic and the effect needs to be applied again with new commands
     */
    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) throws RollBackException {
        int movesLeft = round.getPlayerTurn(0).getMovesLeft();
        int currentTurnNumber = round.getPlayerTurn(0).getTurnNumber();
        String currentPlayer = round.getCurrentPlayer().getName();
        round.getPlayerTurn(0).setMovesLeft(movesLeft++);
        round.getPlayerTurns().removeIf(
                (playerTurn) -> playerTurn.getPlayer().getName().equals(currentPlayer) &&
                playerTurn.getTurnNumber() == currentTurnNumber+1);
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
