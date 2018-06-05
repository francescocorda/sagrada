package it.polimi.ingsw.Model.effects;

import it.polimi.ingsw.Model.Game.Round;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.exceptions.RollBackException;

import java.util.ArrayList;

public class RollDraftPool extends Effect {
    private static String description = "The draft pool will be rolled again.";

    public RollDraftPool() {
        this.stop = false;
        this.commandsLenght = 0;
    }

    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) throws RollBackException {
       table.rollDraftPool();
       table.notifyObservers();
       return true;
    }

    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + description);
    }
}
