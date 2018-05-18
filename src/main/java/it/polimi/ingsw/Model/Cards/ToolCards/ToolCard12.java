package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;

public class ToolCard12 extends ToolCard {

    int countMoves;
    Color color;

    public ToolCard12(){
        ID = 12;
        name = "Tap Wheel";
        description = "Move up to 2 dice of the same\ncolor that match the color of a die\non the RoundTrack\n\nYou must obey all\nplacement restrictions.";
        numOfTokens = 0;
        countMoves = 0;
        color = null;
    }

    @Override
    public Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws DiceNotFoundException {
        int row = Integer.parseInt(commands.remove(0));
        int col = Integer.parseInt(commands.remove(0));

        if(countMoves==0) {

            HashSet<Color> colorsRT = new HashSet<>();
            for (int i = 0; i < roundTrack.size(); i++) {
                ArrayList<Dice> roundDices = roundTrack.getRoundDices(i);
                for (Dice d : roundDices) {
                    colorsRT.add(d.getColor());
                }
            }
            if (colorsRT.contains(player.getWindowFrame().getDice(row, col).getColor())) {
                color = player.getWindowFrame().getDice(row, col).getColor();
                countMoves++;
                SpecialMove specialMove = new SpecialMove(draftPool, player, roundTrack, diceBag, playerTurns);
                specialMove.setToolCard(this);
                playerTurns.get(0).getMoves().add(specialMove);
                return player.getWindowFrame().removeDice(row, col);
            } else {
                throw new DiceNotFoundException(); //forse sarebbe meglio un'eccezione ad hoc
            }

        } else {

            if(color.equals(player.getWindowFrame().getDice(row, col).getColor())) {
                return player.getWindowFrame().removeDice(row, col);
            }else {
                throw new DiceNotFoundException(); //forse sarebbe meglio un'eccezione ad hoc
            }

        }
    }
}