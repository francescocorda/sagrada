package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard10 extends ToolCard {

    public ToolCard10(){
        ID = 10;
        name = "Grinding Stone";
        description = "After drafting, flip the die\nto its opposite side\n\n6flips to1, 5 to 2, 4 to 3, etc.";
        numOfTokens = 0;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws InvalidFaceException {
        int indexDP=Integer.parseInt(commands.remove(0));
        Dice dice = draftPool.remove(indexDP-1);
        dice.setFace(7-dice.valueOf());
        return dice;
    }
}
