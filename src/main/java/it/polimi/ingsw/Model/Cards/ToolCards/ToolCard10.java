package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.DiceBag;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.PlayerTurn;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard10 extends ToolCard {

    public void ToolCard10(){
        ID = 10;
        name = "Grinding Stone";
        description = "After drafting, flip the die\nto its opposite side\n\n6flips to1, 5 to 2, 4 to 3, etc.";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        Dice dice;
        dice = specialMove.chooseDiceFromDP(drawPool, commands);
        try {
            dice.setFace(7-dice.valueOf());
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        try {
            specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(2));
        } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException e) {
            e.printStackTrace();
        }
    }
}
