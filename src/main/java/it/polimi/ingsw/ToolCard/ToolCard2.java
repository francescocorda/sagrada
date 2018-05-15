package it.polimi.ingsw.ToolCard;



import it.polimi.ingsw.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard2 extends ToolCard {
    public void ToolCard2(){
        ID = 2;
        name = "Eglomise Brush";
        description = "Move any one die in your window\nignoring the color restrictions\n\nYoum must obey all other\nplacement restrictions";
        numOfTokens = 0;
    }
    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        Dice dice;
        dice = specialMove.chooseDicefromWindow(player.getWindowFrame(), commands);
        int index = 4;
        boolean done=false;
        while(!done) {
            try {
                specialMove.enableRestriction(player.getWindowFrame(), "COLOR");
                specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(index));
                done=true;
            } catch (InvalidNeighboursException | MismatchedRestrictionException | OccupiedCellException | InvalidFirstMoveException e) {
                index = index + 4;
            }
        }
    }
}
