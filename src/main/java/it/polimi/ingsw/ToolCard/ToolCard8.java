package it.polimi.ingsw.ToolCard;

import it.polimi.ingsw.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard8 extends ToolCard {

    public void ToolCard7(){
        ID = 8;
        name = "Running Pliers";
        description = "After your first turn,\nimmediately draft a die\n\nSkip your next turn this round";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        int check = 0;
        int index, i;
        for(PlayerTurn pT : playerTurns){
            if(check==0 && pT.getPlayer().equals(player) && pT.getActionPerformed().equals(ActionPerformed.DEFAULT)) check = 1;
            if(check==1 && pT.getPlayer().equals(player) && pT.getActionPerformed().equals(ActionPerformed.NOTHING)) check = 2;
        }
        if(check == 2){  //esegue le seguenti istruzioni solo dopo la mossa normale del primo turno
            Dice dice;
            dice = specialMove.chooseDiceFromDP(drawPool, commands);
            try {
                specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(2));
            } catch (InvalidNeighboursException e) {
                e.printStackTrace();
            } catch (OccupiedCellException e) {
                e.printStackTrace();
            } catch (MismatchedRestrictionException e) {
                e.printStackTrace();
            } catch (InvalidFirstMoveException e) {
                e.printStackTrace();
            }
            i=0;
            for(int j=0; j<playerTurns.size();j++){  //cancello il secondo turno del player
                if(i == 0 && playerTurns.get(j).getPlayer().equals(player)) {
                    i=1;
                } else if(i == 1 && playerTurns.get(j).getPlayer().equals(player)) {
                    playerTurns.remove(j);
                }
            }
        }
    }
}
