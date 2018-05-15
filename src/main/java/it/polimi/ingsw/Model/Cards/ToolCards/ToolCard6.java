package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.DiceBag;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.PlayerTurn;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard6 extends ToolCard {  //Da modellare il riponimento del dado impiazzabile in riserva

    public void ToolCard6(){
        ID = 6;
        name = "Flux Brush";
        description = "After drafting,\nre-roll the drafted die\n\nIf it cannot be placed,\nreturn it to the Draft Pool";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        Dice dice;
        dice = specialMove.chooseDiceFromDP(drawPool, commands);
        dice.roll();
        try {
            specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(2));
        } catch (InvalidNeighboursException | OccupiedCellException | MismatchedRestrictionException | InvalidFirstMoveException e) {
            drawPool.add(dice);
        }
    }
}
