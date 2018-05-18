package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard11 extends ToolCard {
    int counter;
    Dice dice;

    public ToolCard11(){
        ID = 11;
        name = "Flux Remover";
        description = "After drafting, return the die to the\nDice Bag and pull 1 die from the bag\n\nChoose a value and place the new die,\nobeying all placement restrictions, or\nreturns it to the Draft Pool";
        numOfTokens = 0;
        counter = 0;
        dice=null;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws InvalidFaceException {
        if(counter == 0) {
            int indexDP = Integer.parseInt(commands.remove(0));
            dice = draftPool.remove(indexDP - 1);
            diceBag.addDice(dice);
            dice = diceBag.draw();
            counter++;
            return null;
        }else if(counter == 1) {
            int face = Integer.parseInt(commands.remove(0));
            dice.setFace(face);
        }
        return dice;
    }
}
