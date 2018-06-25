package it.polimi.ingsw.Model.effects;

import it.polimi.ingsw.Model.Game.Round;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.exceptions.RollBackException;

import java.util.ArrayList;

public class SkipNextTurn extends Effect {
    private static String description = "It's possible to make an additional placement in this turn." +
            "\nThe next turn will be skipped.";

    public SkipNextTurn() {
        this.stop = false;
        this.commandsLenght = 0;
    }

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

    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + description);
    }

    @Override
    public String getActiveTableElement() {
        return "";
    }
}
