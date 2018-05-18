package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;

import java.util.ArrayList;

public class ToolCard5 extends ToolCard {

    public ToolCard5(){
        ID = 5;
        name = "Lens Cutter";
        description = "After drafting, swap the drafted\ndie with a die from the\nRound Track";
        numOfTokens = 0;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) {
        int indexDP = Integer.parseInt(commands.remove(0));
        Dice dice = draftPool.remove(indexDP-1);
        //inedxRT and indexDice starts from value 1
        int indexRT = Integer.parseInt(commands.remove(0));
        int indexDice = Integer.parseInt(commands.remove(0));
        Dice temp = roundTrack.getRoundDices(indexRT-1).remove(indexDice-1);
        roundTrack.getRoundDices(indexRT-1).add(dice);
        return temp;
    }
}
