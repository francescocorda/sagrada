package it.polimi.ingsw.ToolCard;



import it.polimi.ingsw.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard3 extends ToolCard {
    public void ToolCard3(){
        ID = 3;
        name = "Copper Foil Burnisher";
        description = "Move any one die in your window\nignoring shade restriction\n\nYou must obey all other\nplacement restriction";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        Dice dice;
        dice = specialMove.chooseDicefromWindow(player.getWindowFrame(), commands);
        int index = 4;
        boolean done = false;
        while (!done) {
            try {
                specialMove.enableRestriction(player.getWindowFrame(), "VALUE");
                specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(index));
                done=true;
            } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException e) {
                index = index+4;
            }
        }
    }
}
