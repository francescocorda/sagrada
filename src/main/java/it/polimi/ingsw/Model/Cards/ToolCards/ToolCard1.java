package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.DiceBag;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.PlayerTurn;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard1 extends ToolCard {

    public void ToolCard1(){
        ID = 1;
        name = "Grozing Pliers";
        description = "After drafting,\nincrease or decrease the value\nof the drafted die by 1\n\n1 may not change to 6, or 6 to 1";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        Dice dice;
        dice = specialMove.chooseDiceFromDP(drawPool, commands);
        dice = specialMove.incOrDec(dice, commands.substring(2));
        boolean done = false;
        try {
            specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(6));
        } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException e) {
            drawPool.add(dice);
        }

    }
}
