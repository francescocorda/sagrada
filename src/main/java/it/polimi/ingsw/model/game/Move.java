package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.effects.PlaceIn;
import it.polimi.ingsw.model.effects.RemoveFrom;
import it.polimi.ingsw.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;

import static it.polimi.ingsw.model.effects.Element.DRAFTPOOL;
import static it.polimi.ingsw.model.effects.Element.WINDOW;

public class Move implements Serializable {
    private Table table;
    private Round round;
    private RemoveFrom removeFromDP;
    private PlaceIn placeInW;
    private int count;

    /**
     * creates a new {@link Move} from the given parameters.
     * @param table : the given {@link Table} table
     * @param round : the given {@link Round} round
     */
    public Move(Table table, Round round) {
        this.table = table;
        this.round = round;
        removeFromDP = new RemoveFrom(DRAFTPOOL);
        placeInW = new PlaceIn(WINDOW);
        count = 0;
    }

    /**
     * performs the move accordingly to the given {@link ArrayList<String>} commands.
     * @param commands : the given {@link ArrayList<String>} commands
     * @throws ImpossibleMoveException if the given commands are not valid
     */
    public void performMove(ArrayList<String> commands) throws ImpossibleMoveException {
        if (count == 0) {
            if (removeFromDP.applyEffect(commands,table,round)) {
                placeInW.explainEffect(table, round);
                count++;
            } else {
                removeFromDP.explainEffect(table, round);
            }
        } else {
            if (placeInW.applyEffect(commands,table,round)) {
                round.getPlayerTurn(0).setMoveActive(false);
                count = 0;
            } else {
                placeInW.explainEffect(table, round);
            }
        }
    }

    /**
     * explains the effect accordingly to the given parameters.
     * @param table : the given {@link Table} table
     * @param round : the given {@link Round} round
     */
    public void explainEffect(Table table, Round round) {
        if (count==0) {
            removeFromDP.explainEffect(table, round);
        } else {
            placeInW.explainEffect(table, round);
        }
    }

    /**
     * @return the move's ActiveTableElement.
     */
    public String getActiveTableElement() {
        if (count==0) {
            return removeFromDP.getActiveTableElement();
        } else {
            return placeInW.getActiveTableElement();
        }
    }

    /**
     * @return the int commands' length.
     */
    public int getCommandsLength() {
        if(count==0) {
            return removeFromDP.getCommandsLength();
        } else {
            return placeInW.getCommandsLength();
        }
    }
}
