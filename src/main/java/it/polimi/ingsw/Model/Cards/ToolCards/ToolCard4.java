package it.polimi.ingsw.Model.Cards.ToolCards;



import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.DiceBag;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.PlayerTurn;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard4 extends ToolCard {

    public void ToolCard4(){  //Cos'ì com'è permette di spostare due volte lo stesso dado(da aggiustare)
        ID = 4;
        name = "Lathekin";
        description = "Move exactly two dice, obeying\nall placement restrictions";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {

        int index = 4;


        boolean done1 = false;

        Dice dice1 = specialMove.chooseDicefromWindow(player.getWindowFrame(), commands);
        while (!done1) {
            try {
                specialMove.ordinaryMove(player.getWindowFrame(), dice1, drawPool, commands.substring(index));
                done1=true;
            } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException e) {
                e.printStackTrace();
                index=index+4;
            }
        }

        boolean done2 = false;
        index = index+4;
        Dice dice2 = specialMove.chooseDicefromWindow(player.getWindowFrame(), commands.substring(index));
        index = index+4;
        while(!done2) {
            try {
                specialMove.ordinaryMove(player.getWindowFrame(), dice2, drawPool, commands.substring(index));
                done2=true;
            } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException e) {
                index = index + 4;
            }
        }
    }
}
