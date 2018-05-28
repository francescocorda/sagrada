package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.ToolCards.ToolCard;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class SpecialMove extends Move{
    private ArrayList<Dice> draftPool;
    private RoundTrack roundTrack;
    private DiceBag diceBag;
    int old_index1;
    int old_index2;
    String command;

    public SpecialMove(ArrayList<Dice> draftPool, Player player, RoundTrack roundTrack, DiceBag diceBag, ArrayList<PlayerTurn> playerTurns) {
        super(draftPool, player, playerTurns);
        this.draftPool = draftPool;
        this.player = player;
        this.playerTurns = playerTurns;
        this.toolCard = null;
        this.roundTrack = roundTrack;
        this.diceBag = diceBag;
        this.old_index1 = 0;
        this.old_index2 = 0;
        this.command = null;
    }

    @Override
    public void performMove(ArrayList<String> commands) throws InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException, DiceNotFoundException, InvalidFaceException, WrongRoundException {

        //remove command
        if(commands.size()>0) {
            if(commands.get(0).equals("DRAFTPOOL")) {
                command = commands.remove(0);
                old_index1 = Integer.parseInt(commands.get(0));
            } else if(commands.get(0).equals("WINDOW")) {
                command = commands.remove(0);
                old_index1=Integer.parseInt(commands.get(0));
                old_index2=Integer.parseInt(commands.get(1));
            }
        }
        Dice dice = null;
        dice = toolCard.useAbility(draftPool, roundTrack, diceBag, player, playerTurns, commands);

        if (dice != null) {
            int row = Integer.parseInt(commands.remove(0));
            int col = Integer.parseInt(commands.remove(0));
            try {
                player.getWindowFrame().setDice(row, col, dice);
            } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException e) {
                //rollback
                if(command.equals("DRAFTPOOL")) {
                    draftPool.add(old_index1 - 1, dice);
                } else if(command.equals("WINDOW")) {
                    player.getWindowFrame().getPatternCard().setExceptionRestriction(old_index1,old_index2, true);
                    player.getWindowFrame().getPatternCard().setExceptionPosition(old_index1,old_index2, true);
                    player.getWindowFrame().setDice(old_index1, old_index2, dice);
                }
            }
            playerTurns.get(0).getMoves().remove(this);
        }
    }
}
