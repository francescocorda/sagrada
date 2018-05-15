package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;

public class ToolCard12 extends ToolCard {

    public void ToolCard12(){
        ID = 12;
        name = "Tap Wheel";
        description = "Move up to dice of the same\ncolor that match the color of a die\non the RoundTrack\n\nYou must obey all\nplacement restrictions.";
        numOfTokens = 0;
    }

    @Override
    public void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) {
        HashSet<Color> temp = new HashSet<>();
        Dice dice;
        for(ArrayList<Dice> round : roundTrack){
            for(Dice turn : round){
                temp.add(turn.getColor());
            }
        }
        int index = 0;
        for(int i=0; i<2; i++){
            dice = specialMove.chooseDicefromWindow(player.getWindowFrame(), commands.substring(index));
            index += 4;
            Color color = null;
            if(temp.contains(dice.getColor())) {
                try {
                    specialMove.ordinaryMove(player.getWindowFrame(), dice, drawPool, commands.substring(index));
                    index += 4;
                } catch (InvalidNeighboursException | OccupiedCellException | MismatchedRestrictionException | InvalidFirstMoveException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}