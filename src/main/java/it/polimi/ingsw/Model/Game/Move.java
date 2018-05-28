package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.ToolCards.ToolCard;
import it.polimi.ingsw.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Move implements Serializable {
    private Table table;
    private ArrayList<Dice> draftPool;
    protected Player player;
    protected ArrayList<PlayerTurn> playerTurns;
    protected ToolCard toolCard;


    public Move(ArrayList<Dice> draftPool, Player player, ArrayList<PlayerTurn> playerTurns) {
        this.draftPool = new ArrayList<>();
        this.playerTurns = new ArrayList<>();
        this.draftPool = draftPool;
        this.table = new Table();
        table.setDraftPool(draftPool);
        this.player = player;
        this.playerTurns = playerTurns;
        toolCard = null;
    }


    public Move(Table table, Player player) {
        this.table = table;
        this.draftPool = new ArrayList<>();
        this.playerTurns = new ArrayList<>();
        this.player = player;
        toolCard = null;
    }


    public ArrayList<PlayerTurn> getPlayerTurns() {
        return playerTurns;
    }

    public void setPlayerTurns(ArrayList<PlayerTurn> playerTurns) {
        this.playerTurns = playerTurns;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public void setToolCard(ToolCard toolCard) {
        this.toolCard = toolCard;
    }

    //indexDP starts from value 1
    public void performMove(ArrayList<String> commands) throws  MismatchedRestrictionException, InvalidNeighboursException, OccupiedCellException, InvalidFirstMoveException, DiceNotFoundException, InvalidFaceException, WrongRoundException {
        int indexDP = Integer.parseInt(commands.remove(0));
        Dice dice = table.getDraftPool().remove(indexDP-1);
        int row = Integer.parseInt(commands.remove(0));
        int col = Integer.parseInt(commands.remove(0));
        player.getWindowFrame().setDice(row, col, dice);
        playerTurns.get(0).getMoves().remove(this);
    }
}
