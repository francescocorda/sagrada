package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard6 extends ToolCard {  //Da modellare il riponimento del dado impiazzabile in riserva
    Dice dice;
    int counter;

    public ToolCard6(){
        ID = 6;
        name = "Flux Brush";
        description = "After drafting,\nre-roll the drafted die\n\nIf it cannot be placed,\nreturn it to the Draft Pool";
        numOfTokens = 0;
        dice = null;
        counter = 0;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands){
        if(counter==0) {    //during the first call of this method, it's impossible for the player to know where to place the dice in the window
                            //because the position depends on the dice's face. So I store the dice in this class and wait for the second call to return
                            //the result to the move.
            int indexDP = Integer.parseInt(commands.remove(0));
            dice = draftPool.remove(indexDP - 1);
            dice.roll();
            counter++;
            return null;
        } else {
            return dice;
        }
    }
}
