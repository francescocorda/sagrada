package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard2 extends ToolCard {

    public ToolCard2(){
        ID = 2;
        name = "Eglomise Brush";
        description = "Move any one die in your window\nignoring the color restrictions\n\nYou must obey all other\nplacement restrictions";
        numOfTokens = 0;
    }
    @Override
    public Dice useAbility(ArrayList<Dice> drawPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws DiceNotFoundException {
        int row = Integer.parseInt(commands.remove(0));
        int col = Integer.parseInt(commands.remove(0));
        player.getWindowFrame().enableRestriction("COLOR");
        return player.getWindowFrame().removeDice(row, col);
    }
}
