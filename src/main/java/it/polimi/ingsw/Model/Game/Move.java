package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.ToolCards.ToolCard;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class Move {
    protected ArrayList<Dice> draftPool;
    protected Player player;
    protected ArrayList<PlayerTurn> playerTurns;
    protected ToolCard toolCard;


    public Move(ArrayList<Dice> draftPool, Player player, ArrayList<PlayerTurn> playerTurns) {
        this.draftPool = draftPool;
        this.player = player;
        this.playerTurns = playerTurns;
        toolCard = null;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public void setToolCard(ToolCard toolCard) {
        this.toolCard = toolCard;
    }

    //indexDP starts from value 1
    public void performMove(ArrayList<String> commands) throws DiceNotFoundException, InvalidFaceException, MismatchedRestrictionException, InvalidNeighboursException, OccupiedCellException, InvalidFirstMoveException, WrongRoundException {
        int indexDP=Integer.parseInt(commands.remove(0));
        Dice dice = draftPool.remove(indexDP-1);
        int row = Integer.parseInt(commands.remove(0));
        int col = Integer.parseInt(commands.remove(0));
        try {
            player.getWindowFrame().setDice(row, col, dice);
            playerTurns.get(0).getMoves().remove(this);
        } catch (MismatchedRestrictionException | InvalidNeighboursException | InvalidFirstMoveException | OccupiedCellException e) {
            //rollback
            draftPool.add(indexDP-1, dice);
        }
    }
}
