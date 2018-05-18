package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard3 extends ToolCard {

    public ToolCard3(){
        ID = 3;
        name = "Copper Foil Burnisher";
        description = "Move any one die in your window\nignoring shade restriction\n\nYou must obey all other\nplacement restriction";
        numOfTokens = 0;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> drawPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws DiceNotFoundException {
        int row = Integer.valueOf(commands.remove(0));
        int col = Integer.valueOf(commands.remove(0));
        player.getWindowFrame().enableRestriction("VALUE");
        return player.getWindowFrame().removeDice(row, col);
    }
}
