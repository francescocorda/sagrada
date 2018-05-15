package it.polimi.ingsw.ToolCard;

import it.polimi.ingsw.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard11 extends ToolCard {

    public void ToolCard11(){
        ID = 11;
        name = "Flux Remover";
        description = "After drafting, return the die to the\nDice Bag and pull 1 die from the bag\n\nChoose a value and place the new die,\nobeying all placement restrictions, or\nreturns it to the Draft Pool";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        diceBag.addDice(specialMove.chooseDiceFromDP(drawPool, commands));
        Dice dice = diceBag.draw();
        specialMove.setFace(dice,commands.substring(2));
        try {
            specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(4));
        } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException e) {
            e.printStackTrace();
        }
    }
}
