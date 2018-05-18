package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class ToolCard8 extends ToolCard {

    public ToolCard8(){
        ID = 8;
        name = "Running Pliers";
        description = "After your first turn,\nimmediately draft a die\n\nSkip your next turn this round";
        numOfTokens = 0;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws WrongRoundException {
        int check = 0;
        for(PlayerTurn temp : playerTurns){
            if(temp.getPlayer().equals(player)) check++;
        }
        if(check == 2 && playerTurns.get(0).getMoves().size() == 1){
            int i = 0;
            for(int j = 0; j<playerTurns.size(); j++) {
                //delete the player's second turn
                if (i == 0 && playerTurns.get(j).getPlayer().equals(player)) {
                    i = 1;
                } else if (i == 1 && playerTurns.get(j).getPlayer().equals(player)) {
                    playerTurns.remove(j);
                }
            }
            //after first round's ordinary move
            int indexDP=Integer.valueOf(commands.remove(0));
            return draftPool.remove(indexDP-1);
        } else {
            throw new WrongRoundException();
        }
    }
}
