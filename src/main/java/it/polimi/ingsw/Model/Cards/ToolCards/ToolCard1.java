package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.InvalidFaceException;

import java.util.ArrayList;

public class ToolCard1 extends ToolCard {

    public ToolCard1(){
        ID = 1;
        name = "Grozing Pliers";
        description = "After drafting,\nincrease or decrease the value\nof the drafted die by 1\n\n1 may not change to 6, or 6 to 1";
        numOfTokens = 0;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws InvalidFaceException {
        int indexDP = Integer.parseInt(commands.remove(0));
        Dice dice = draftPool.get(indexDP-1);
        String command = commands.remove(0);
        if(command.equals("INC")) dice.setFace(dice.valueOf()+1);
        else if(command.equals("DEC")) dice.setFace(dice.valueOf()-1);
        draftPool.remove(indexDP-1);
        return dice;  //controller has to send "INC" or "DEC" commands
    }
}