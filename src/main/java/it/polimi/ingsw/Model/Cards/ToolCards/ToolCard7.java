
package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard7 extends ToolCard {  //da chiamare solo se è il secondo turno del round del giocatore(forse bisogna avere Round?)

    int countMoves;

    public ToolCard7(){
        ID = 7;
        name = "Glazing Hammer";
        description = "Re-roll all dice in the Draft Pool\n\nThis may only be used on your\nsecond turn before drafting";
        numOfTokens = 0;
        countMoves = 0;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws WrongRoundException, DiceNotFoundException, InvalidNeighboursException, MismatchedRestrictionException, OccupiedCellException, InvalidFaceException, InvalidFirstMoveException {
        if(countMoves==0) {
            int check=0;
            for(PlayerTurn temp : playerTurns){
                if(temp.getPlayer().equals(player)) check++;
            }
            if (check == 1 && playerTurns.get(0).getMoves().size() == 1) {
                for (Dice dice : draftPool) {
                    dice.roll();
                }
                countMoves++;
                return null;
            } else {
                throw new WrongRoundException();    //you can't use the card in this moment
            }
        } else {
            int indexDP=Integer.parseInt(commands.remove(0));
            countMoves = 0;
            return draftPool.remove(indexDP-1);
        }
    }
}