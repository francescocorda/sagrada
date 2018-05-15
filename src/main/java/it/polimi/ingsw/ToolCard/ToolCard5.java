package it.polimi.ingsw.ToolCard;

import it.polimi.ingsw.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard5 extends ToolCard {
    public void ToolCard5(){
        ID = 5;
        name = "Lens Cutter";
        description = "After drafting, swap the drafted\ndie with a die from the\nRound Track";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        Dice dice;
        dice = specialMove.chooseDiceFromDP(drawPool, commands);
        dice = specialMove.exchangeDiceRT(roundTrack, dice, commands.substring(2));
        try {
            specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(5));
        } catch (InvalidNeighboursException | OccupiedCellException | MismatchedRestrictionException | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
    }
}
