package it.polimi.ingsw.model.effects;


import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.model.effects.RestrictionException.*;

public class EnableException extends Effect{

    private static final HashMap<RestrictionException, String> descriptions = createMap();

    static HashMap<RestrictionException, String> createMap() {
        HashMap<RestrictionException, String> temp = new HashMap<>();
        temp.put(COLOR, "The color restrictions in the window will be ignored in the next placement.");
        temp.put(FACE, "The number restrictions in the window will be ignored in the next placement.");
        temp.put(POSITION, "The position restrictions in the window will be ignored in the next placement.");
        return temp;
    }

    private RestrictionException exception;

    public EnableException(RestrictionException exception) {
        this.exception = exception;
        this.stop = false;
        this.commandsLenght = 0;
    }

    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) {
        round.getCurrentPlayer().getWindowFrame().enableException(exception.getException());
        return true;
    }

    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + descriptions.get(exception));
    }

    @Override
    public String getActiveTableElement() {
        return exception.toString();
    }
}
