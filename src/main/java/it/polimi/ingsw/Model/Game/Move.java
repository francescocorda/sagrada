package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.effects.PlaceIn;
import it.polimi.ingsw.Model.effects.RemoveFrom;
import it.polimi.ingsw.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;

import static it.polimi.ingsw.Model.effects.Element.DRAFTPOOL;
import static it.polimi.ingsw.Model.effects.Element.WINDOW;

public class Move implements Serializable {
    private Table table;
    private PlayerTurn playerTurn;
    private Round round;
    private RemoveFrom removeFromDP;
    private PlaceIn placeInW;
    private int count;

    public Move(Table table, Round round) {
        this.table = table;
        this.playerTurn = null;
        this.round = round;
        removeFromDP = new RemoveFrom(DRAFTPOOL);
        placeInW = new PlaceIn(WINDOW);
        count = 0;
    }

    public void performMove(ArrayList<String> commands) {
        if (count == 0) {
            try {
                if (removeFromDP.applyEffect(commands,table,round)) {
                    placeInW.explainEffect(table, round);
                    count++;
                } else {
                    removeFromDP.explainEffect(table, round);
                }
            } catch (ImpossibleMoveException e) {
                round.getPlayerTurn(0).setMoveActive(false);
                round.getPlayerTurn(0).setMovesLeft(round.getPlayerTurn(0).getMovesLeft()-1);
            }
        } else {
            if (placeInW.applyEffect(commands,table,round)) {
                round.getPlayerTurn(0).setMoveActive(false);
                round.getPlayerTurn(0).setMovesLeft(round.getPlayerTurn(0).getMovesLeft()-1);
                count = 0;
            } else {
                placeInW.explainEffect(table, round);
            }
        }
    }

    public void explainEffect(Table table, Round round) {
        if (count==0) {
            removeFromDP.explainEffect(table, round);
        } else {
            placeInW.explainEffect(table, round);
        }
    }


    public int getCommandsLenght() {
        if(count==0) {
            return removeFromDP.getCommandsLenght();
        } else {
            return placeInW.getCommandsLenght();
        }
    }
}
