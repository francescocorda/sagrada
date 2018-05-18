package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard9 extends ToolCard {

    public ToolCard9(){
        ID = 9;
        name = "Cork-backed Straightedge";
        description = "After drafting, place the\ndie in a spot that is not adjacent to\nanother die\n\nYou must obey all other\nplacement restrictions";
        numOfTokens = 0;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws DiceNotFoundException {
        int indexDP = Integer.valueOf(commands.remove(0));
        player.getWindowFrame().enableRestriction("POSITION");
        return draftPool.remove(indexDP-1);
    }
}
