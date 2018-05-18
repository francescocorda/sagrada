package it.polimi.ingsw.Model.Cards.ToolCards;



import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard4 extends ToolCard {
    int countMoves;

    public ToolCard4(){  //Cos'ì com'è permette di spostare due volte lo stesso dado(da aggiustare)
        ID = 4;
        name = "Lathekin";
        description = "Move exactly two dice, obeying\nall placement restrictions";
        numOfTokens = 0;
        countMoves = 0;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws DiceNotFoundException {
        int row = Integer.parseInt(commands.remove(0));
        int col = Integer.parseInt(commands.remove(0));
        Dice dice = player.getWindowFrame().removeDice(row, col);
        countMoves++;
        if(countMoves==1){
            SpecialMove specialMove = new SpecialMove(draftPool, player, roundTrack, diceBag, playerTurns);
            specialMove.setToolCard(this);
            playerTurns.get(0).getMoves().add(specialMove);
        }
        return dice;
    }
}
