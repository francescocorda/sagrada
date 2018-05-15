package it.polimi.ingsw.ToolCard;

import it.polimi.ingsw.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard9 extends ToolCard {
    public void ToolCard9(){
        ID = 9;
        name = "Cork-backed Straightedge";
        description = "After drafting, place the\ndie in a spot that is not adjacent to\nanother die\n\nYou must obey all other\nplacement restrictions";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        Dice dice;
        dice = specialMove.chooseDiceFromDP(drawPool, commands);
        specialMove.enableRestriction(player.getWindowFrame(), "POSITION");
        try {
            specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(2));
        } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException e) {
            e.printStackTrace();
        }
    }
}
