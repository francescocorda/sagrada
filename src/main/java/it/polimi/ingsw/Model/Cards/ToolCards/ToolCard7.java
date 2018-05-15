package it.polimi.ingsw.Model.Cards.ToolCards;


import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard7 extends ToolCard {  //da chiamare solo se è il secondo turno del round del giocatore(forse bisogna avere Round?)

    public void ToolCard7(){
        ID = 7;
        name = "Glazing Hammer";
        description = "Re-roll all dice in the Draft Pool\n\nThis may only be used on your\nsecond turn before drafting";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) throws WrongRoundException {
        int check = 0;
        for(PlayerTurn pT : playerTurns){
            if(check==0 && pT.getPlayer().equals(player) && pT.getActionPerformed().equals(ActionPerformed.COMPLETED)) check = 1;
            if(check==1 && pT.getPlayer().equals(player) && pT.getActionPerformed().equals(ActionPerformed.DEFAULT)) check = 2;
        }
        if(check == 2){
            Dice dice;
            for(Dice d : drawPool) d.roll();
            dice = specialMove.chooseDiceFromDP(drawPool, commands);
            try {
                specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(2));
            } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException e) {
                e.printStackTrace();
            }
        } else {
            throw new WrongRoundException();
        }
    }
}